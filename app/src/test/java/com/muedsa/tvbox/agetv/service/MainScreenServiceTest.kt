package com.muedsa.tvbox.agetv.service

import com.muedsa.tvbox.agetv.TestPlugin
import com.muedsa.tvbox.agetv.checkMediaCardRows
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MainScreenServiceTest {

    private val service = TestPlugin.provideMainScreenService()

    @Test
    fun getRowsDataTest() = runTest{
        val rows = service.getRowsData()
        checkMediaCardRows(rows = rows)
    }

}