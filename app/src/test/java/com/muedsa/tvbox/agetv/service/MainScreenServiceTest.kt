package com.muedsa.tvbox.agetv.service

import com.muedsa.tvbox.agetv.AgeMobileApiUrl
import com.muedsa.tvbox.tool.createJsonRetrofit
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MainScreenServiceTest {

    private val ageApiService by lazy {
        createJsonRetrofit(
            baseUrl = AgeMobileApiUrl,
            service = AgeApiService::class.java,
            debug = true
        )
    }

    private val service = MainScreenService(ageApiService)

    @Test
    fun getRowsDataTest() = runTest{
        val rows = service.getRowsData()
        rows.forEach { row ->
            println("${row.title} ${row.cardWidth}X${row.cardHeight} ${row.cardType}")
            row.list.forEach {
                println("${it.title} ${it.id} ${it.detailUrl} ${it.coverImageUrl}")
            }
        }
    }

}