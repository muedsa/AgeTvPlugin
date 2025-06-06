package com.muedsa.tvbox.agetv

import com.muedsa.tvbox.agetv.service.AgeApiService
import com.muedsa.tvbox.agetv.service.AgeUrlService
import com.muedsa.tvbox.agetv.service.MainScreenService
import com.muedsa.tvbox.agetv.service.MediaCatalogService
import com.muedsa.tvbox.agetv.service.MediaDetailService
import com.muedsa.tvbox.agetv.service.MediaSearchService
import com.muedsa.tvbox.api.plugin.IPlugin
import com.muedsa.tvbox.api.plugin.PluginOptions
import com.muedsa.tvbox.api.plugin.TvBoxContext
import com.muedsa.tvbox.api.service.IMainScreenService
import com.muedsa.tvbox.api.service.IMediaCatalogService
import com.muedsa.tvbox.api.service.IMediaDetailService
import com.muedsa.tvbox.api.service.IMediaSearchService
import com.muedsa.tvbox.tool.IPv6Checker
import com.muedsa.tvbox.tool.PluginCookieJar
import com.muedsa.tvbox.tool.SharedCookieSaver
import com.muedsa.tvbox.tool.createJsonRetrofit
import com.muedsa.tvbox.tool.createOkHttpClient

class AgeTvPlugin(tvBoxContext: TvBoxContext) : IPlugin(tvBoxContext = tvBoxContext) {

    override var options: PluginOptions = PluginOptions(enableDanDanPlaySearch = true)

    override suspend fun onInit() {}

    override suspend fun onLaunched() {}

    private val cookieJar by lazy { PluginCookieJar(saver = SharedCookieSaver(store = tvBoxContext.store)) }
    private val okHttpClient by lazy {
        createOkHttpClient(
            debug = tvBoxContext.debug,
            cookieJar = cookieJar,
            onlyIpv4 = tvBoxContext.iPv6Status != IPv6Checker.IPv6Status.SUPPORTED
        )
    }

    private val ageUrlService by lazy {
        AgeUrlService(okHttpClient = okHttpClient)
    }

    private val ageApiService by lazy {
        createJsonRetrofit(
            baseUrl = ageUrlService.mobileApiUrl,
            service = AgeApiService::class.java,
            okHttpClient = okHttpClient
        )
    }
    private val mainScreenService by lazy { MainScreenService(ageApiService) }
    private val mediaDetailService by lazy {
        MediaDetailService(
            ageUrlService = ageUrlService,
            ageApiService = ageApiService,
            okHttpClient = okHttpClient,
            cookieJar = cookieJar,
            debug = tvBoxContext.debug
        )
    }
    private val mediaSearchService by lazy { MediaSearchService(ageApiService) }
    private val mediaCatalogService by lazy { MediaCatalogService(ageApiService) }

    override fun provideMainScreenService(): IMainScreenService = mainScreenService

    override fun provideMediaDetailService(): IMediaDetailService = mediaDetailService

    override fun provideMediaSearchService(): IMediaSearchService = mediaSearchService

    override fun provideMediaCatalogService(): IMediaCatalogService = mediaCatalogService
}