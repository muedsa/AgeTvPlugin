package com.muedsa.tvbox.agetv

import com.muedsa.tvbox.agetv.service.AgeApiService
import com.muedsa.tvbox.agetv.service.MainScreenService
import com.muedsa.tvbox.agetv.service.MediaDetailService
import com.muedsa.tvbox.agetv.service.MediaSearchService
import com.muedsa.tvbox.api.plugin.IPlugin
import com.muedsa.tvbox.api.plugin.PluginOptions
import com.muedsa.tvbox.api.plugin.TvBoxContext
import com.muedsa.tvbox.api.service.IMainScreenService
import com.muedsa.tvbox.api.service.IMediaDetailService
import com.muedsa.tvbox.api.service.IMediaSearchService
import com.muedsa.tvbox.tool.createJsonRetrofit

class AgeTvPlugin(tvBoxContext: TvBoxContext) : IPlugin(tvBoxContext = tvBoxContext) {

    override var options: PluginOptions = PluginOptions(enableDanDanPlaySearch = true)

    private val ageApiService by lazy {
        createJsonRetrofit(
            baseUrl = AgeMobileApiUrl,
            service = AgeApiService::class.java,
            debug = tvBoxContext.debug
        )
    }
    private val mainScreenService by lazy { MainScreenService(ageApiService) }
    private val mediaDetailService by lazy { MediaDetailService(ageApiService, tvBoxContext.debug) }
    private val mediaSearchService by lazy { MediaSearchService(ageApiService) }

    override fun provideMainScreenService(): IMainScreenService = mainScreenService

    override fun provideMediaDetailService(): IMediaDetailService = mediaDetailService

    override fun provideMediaSearchService(): IMediaSearchService = mediaSearchService
}