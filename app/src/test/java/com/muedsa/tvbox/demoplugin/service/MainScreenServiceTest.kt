package com.muedsa.tvbox.demoplugin.service

import com.muedsa.tvbox.tool.createJsonRetrofit
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MainScreenServiceTest {

    private val danDanPlayApiService by lazy {
        createJsonRetrofit(
            baseUrl = "https://api.dandanplay.net/api/",
            service = DanDanPlayApiService::class.java,
            debug = true
        )
    }

    private val service = MainScreenService(danDanPlayApiService)

    @Test
    fun getRowsDataTest() = runTest{
        val rows = service.getRowsData()
        rows.forEach { row ->
            println("${row.title} ${row.cardWidth}X${row.cardHeight} ${row.cardType}")
            row.list.forEach {
                println("${it.title} ${it.id} ${it.detailUrl}")
            }
        }
    }

}