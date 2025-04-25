package com.muedsa.tvbox.agetv.service

import com.muedsa.tvbox.tool.decodeBase64ToStr
import com.muedsa.tvbox.tool.encodeBase64
import com.muedsa.tvbox.tool.feignChrome
import com.muedsa.tvbox.tool.get
import com.muedsa.tvbox.tool.toRequestBuild
import okhttp3.OkHttpClient

class AgeUrlService(
    private val okHttpClient: OkHttpClient,
) {

    private val redirectHost: String? by lazy {
        AGE_TV_URL.toRequestBuild()
            .feignChrome()
            .get(okHttpClient = okHttpClient)
            .request
            .url
            .host
    }

    val mobileUrl: String by lazy {
        redirectHost?.replace("www", "m")?.let { "https://$it/" } ?: AGE_MOBILE_URL
    }

    val mobileApiUrl: String by lazy {
        redirectHost?.replace("www", "api")?.let { "https://$it/" } ?: AGE_MOBILE_API_URL
    }

    val mobileUrlBase64: String by lazy {
        mobileUrl.toByteArray(Charsets.UTF_8).encodeBase64()
    }

    companion object {
        const val AGE_TV_URL = "https://age.tv/"
        const val AGE_MOBILE_URL_BASE64 = "aHR0cHM6Ly9tLmFnZWZhbnMubGEv"
        val AGE_MOBILE_URL = AGE_MOBILE_URL_BASE64.decodeBase64ToStr()
        const val AGE_MOBILE_API_URL_BASE64 = "aHR0cHM6Ly9hcGkuYWdlZmFucy5sYS8="
        val AGE_MOBILE_API_URL = AGE_MOBILE_API_URL_BASE64.decodeBase64ToStr()
    }
}