package com.muedsa.tvbox.demoplugin

import com.muedsa.tvbox.api.plugin.IPlugin
import com.muedsa.tvbox.api.plugin.PluginOptions
import com.muedsa.tvbox.api.plugin.TvBoxContext
import com.muedsa.tvbox.api.service.IMainScreenService
import com.muedsa.tvbox.api.service.IMediaDetailService
import com.muedsa.tvbox.api.service.IMediaSearchService
import com.muedsa.tvbox.demoplugin.service.DanDanPlayApiService
import com.muedsa.tvbox.demoplugin.service.MainScreenService
import com.muedsa.tvbox.demoplugin.service.MediaDetailService
import com.muedsa.tvbox.demoplugin.service.MediaSearchService
import com.muedsa.tvbox.tool.createJsonRetrofit

class DemoPlugin(tvBoxContext: TvBoxContext) : IPlugin(tvBoxContext = tvBoxContext) {

    override var options: PluginOptions = PluginOptions(enableDanDanPlaySearch = true)

    private val danDanPlayApiService by lazy {
        createJsonRetrofit(
            baseUrl = "https://api.dandanplay.net/api/",
            service = DanDanPlayApiService::class.java,
            debug = tvBoxContext.debug
        )
    }
    private val mainScreenService by lazy { MainScreenService(danDanPlayApiService) }
    private val mediaDetailService by lazy { MediaDetailService(danDanPlayApiService) }
    private val mediaSearchService by lazy { MediaSearchService(danDanPlayApiService) }

    override fun provideMainScreenService(): IMainScreenService = mainScreenService

    override fun provideMediaDetailService(): IMediaDetailService = mediaDetailService

    override fun provideMediaSearchService(): IMediaSearchService = mediaSearchService
}