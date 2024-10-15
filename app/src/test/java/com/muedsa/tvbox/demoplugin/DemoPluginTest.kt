package com.muedsa.tvbox.demoplugin

import com.muedsa.tvbox.api.plugin.TvBoxContext
import org.junit.Test

class DemoPluginTest {

    private fun create() = DemoPlugin(TvBoxContext(1080, 1920, true))

    @Test
    fun create_test() {
        create()
    }

    @Test
    fun provideMainScreenService_test() {
        create().provideMainScreenService()
    }

    @Test
    fun provideMediaDetailService_test() {
        create().provideMediaDetailService()
    }

    @Test
    fun provideMediaSearchService_test() {
        create().provideMediaSearchService()
    }
}