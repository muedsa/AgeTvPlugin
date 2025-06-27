package com.muedsa.tvbox.agetv.service

import com.muedsa.tvbox.tool.createOkHttpClient
import org.junit.Test

class AgeUrlServiceTest {
    private val service = AgeUrlService(createOkHttpClient())

    @Test
    fun tryGetUrl_test() {
        val url = service.tryGetUrl()
        check(url.isNotBlank())
    }

    @Test
    fun parseUrlFormPublicPage_page1_test() {
        val pageUrl = AgeUrlService.AGE_URL_PUBLIC_PAGE_1
        val url = service.parseUrlFormPublicPage(pageUrl)
        println("url: $url from page $pageUrl")
        check(url.isNotBlank())
    }

    @Test
    fun parseUrlFormPublicPage_page2_test() {
        val pageUrl = AgeUrlService.AGE_URL_PUBLIC_PAGE_2
        val url = service.parseUrlFormPublicPage(pageUrl)
        println("url: $url from page $pageUrl")
        check(url.isNotBlank())
    }

    @Test
    fun parseUrlFormGithubReadme_page1_test() {
        val pageUrl = AgeUrlService.AGE_URL_PUBLIC_GITHUB_1
        val url = service.parseUrlFormGithubReadme(pageUrl)
        println("url: $url from page $pageUrl")
        check(url.isNotBlank())
    }

    @Test
    fun parseUrlFormGithubReadme_page2_test() {
        val pageUrl = AgeUrlService.AGE_URL_PUBLIC_GITHUB_2
        val url = service.parseUrlFormGithubReadme(pageUrl)
        println("url: $url from page $pageUrl")
        println("url: $url from page $pageUrl")
        check(url.isNotBlank())
    }

    @Test
    fun parseUrlFormGithubReadme_page3_test() {
        val pageUrl = AgeUrlService.AGE_URL_PUBLIC_GITHUB_3
        val url = service.parseUrlFormGithubReadme(pageUrl)
        println("url: $url from page $pageUrl")
        check(url.isNotBlank())
    }
}