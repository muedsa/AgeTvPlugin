package com.muedsa.tvbox.agetv.service

import com.muedsa.tvbox.tool.checkSuccess
import com.muedsa.tvbox.tool.decodeBase64ToStr
import com.muedsa.tvbox.tool.encodeBase64
import com.muedsa.tvbox.tool.feignChrome
import com.muedsa.tvbox.tool.get
import com.muedsa.tvbox.tool.parseHtml
import com.muedsa.tvbox.tool.stringBody
import com.muedsa.tvbox.tool.toRequestBuild
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient

class AgeUrlService(
    private val okHttpClient: OkHttpClient,
) {

    private val redirectHost: String? by lazy { tryGetUrl() }

    val mobileUrl: String by lazy {
        redirectHost?.replace("www", "m")?.let { "https://$it/" } ?: AGE_MOBILE_URL
    }

    val mobileApiUrl: String by lazy {
        redirectHost?.replace("www", "api")?.let { "https://$it/" } ?: AGE_MOBILE_API_URL
    }

    val mobileUrlBase64: String by lazy {
        mobileUrl.toByteArray(Charsets.UTF_8).encodeBase64()
    }

    fun tryGetUrl(): String {
        var url = parseUrlFormPublicPage(AGE_URL_PUBLIC_PAGE_1)
        if (url.isBlank()) {
            url = parseUrlFormPublicPage(AGE_URL_PUBLIC_PAGE_2)
        }
        if (url.isBlank()) {
            url = parseUrlFormGithubReadme(AGE_URL_PUBLIC_GITHUB_1)
        }
        if (url.isBlank()) {
            url = parseUrlFormGithubReadme(AGE_URL_PUBLIC_GITHUB_2)
        }
        if (url.isBlank()) {
            url = parseUrlFormGithubReadme(AGE_URL_PUBLIC_GITHUB_3)
        }
        check(url.isNotBlank()) { "获取AGETV地址失败" }
        return url.toHttpUrl().host
    }

    fun parseUrlFormPublicPage(url: String): String {
        return try {
            val body = url.toRequestBuild()
                .feignChrome()
                .get(okHttpClient = okHttpClient)
                .checkSuccess()
                .parseHtml()
                .body()
            val linkEl = body.selectFirst("blockquote p > a")
            linkEl?.text()?.trim() ?: ""
        } catch (_: Throwable) { "" }
    }

    fun parseUrlFormGithubReadme(url: String): String {
        return try {
            val content = url.toRequestBuild()
                .feignChrome()
                .get(okHttpClient = okHttpClient)
                .checkSuccess()
                .stringBody()
            GITHUB_README_URL_REGEX.find(content)?.groups?.get(1)?.value ?: ""
        } catch (_: Throwable) { "" }
    }

    companion object {
        const val AGE_URL_PUBLIC_PAGE_1 = "https://rentry.org/agefans"
        const val AGE_URL_PUBLIC_PAGE_2 = "https://rentry.la/agefans"
        const val AGE_URL_PUBLIC_GITHUB_1 = "https://ghfast.top/https://raw.githubusercontent.com/agefanscom/website/refs/heads/main/README.md"
        const val AGE_URL_PUBLIC_GITHUB_2 = "https://gh-proxy.com/raw.githubusercontent.com/agefanscom/website/refs/heads/main/README.md"
        const val AGE_URL_PUBLIC_GITHUB_3 = "https://raw.githubusercontent.com/agefanscom/website/refs/heads/main/README.md"
        val GITHUB_README_URL_REGEX = "> 最新域名：\\[(.*?)\\]\\(".toRegex()
        const val AGE_MOBILE_URL_BASE64 = "aHR0cHM6Ly9tLmFnZWZhbnMubGEv"
        val AGE_MOBILE_URL = AGE_MOBILE_URL_BASE64.decodeBase64ToStr()
        const val AGE_MOBILE_API_URL_BASE64 = "aHR0cHM6Ly9hcGkuYWdlZmFucy5sYS8="
        val AGE_MOBILE_API_URL = AGE_MOBILE_API_URL_BASE64.decodeBase64ToStr()
    }
}