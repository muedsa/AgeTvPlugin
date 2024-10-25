package com.muedsa.tvbox.agetv

import com.muedsa.tvbox.api.plugin.TvBoxContext

val TestPlugin by lazy {
    AgeTvPlugin(
        tvBoxContext = TvBoxContext(
            screenWidth = 1920,
            screenHeight = 1080,
            debug = true,
            store = FakePluginPrefStore()
        )
    )
}