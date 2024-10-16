package com.muedsa.tvbox.agetv

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import timber.log.Timber

class FakeCookieJar : CookieJar {
    private val store: MutableMap<String, List<Cookie>> = mutableMapOf()

    @Synchronized
    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val cookies = store[url.host] ?: emptyList()
        cookies.forEach {
            Timber.d("$url send cookie ${it.name}=${it.value}")
        }
        return store[url.host] ?: emptyList()
    }

    @Synchronized
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookies.forEach {
            Timber.d("${url.host} save cookie ${it.name}=${it.value}")
        }
        store[url.host] = filterCookies(cookies)
    }

    private fun filterCookies(cookies: List<Cookie>): List<Cookie> {
        val names = mutableSetOf<String>()
        val newCookies = mutableListOf<Cookie>()
        cookies.forEach {
            if (!names.contains(it.name)) {
                newCookies.add(it)
                names.add(it.name)
            }
        }
        return newCookies
    }
}