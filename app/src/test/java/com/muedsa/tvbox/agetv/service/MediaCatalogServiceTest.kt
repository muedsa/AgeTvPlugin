package com.muedsa.tvbox.agetv.service

import com.muedsa.tvbox.agetv.TestPlugin
import com.muedsa.tvbox.agetv.checkMediaCard
import com.muedsa.tvbox.api.data.MediaCatalogOption
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MediaCatalogServiceTest {


    private val service = TestPlugin.provideMediaCatalogService()

    @Test
    fun getConfig_test() = runTest{
        val config = service.getConfig()
        check(config.initKey == "1")
        check(config.pageSize > 0)
        check(config.cardWidth > 0)
        check(config.cardHeight > 0)
        check(config.catalogOptions.isNotEmpty())
    }

    @Test
    fun catalog_test() = runTest {
        val config = service.getConfig()
        val options = MediaCatalogOption.getDefault(config.catalogOptions)
        val combineList = buildList{
            service.catalog(options = options, loadKey = "1", loadSize = 10).also {
                check(it.prevKey == null)
                check(it.nextKey == "2")
                check(it.list.isNotEmpty())
                it.list.forEach { card -> checkMediaCard(card = card, cardType = config.cardType) }
                addAll(it.list)
            }
            service.catalog(options = options, loadKey = "2", loadSize = 10).also {
                check(it.prevKey == "1")
                check(it.nextKey == "3")
                check(it.list.isNotEmpty())
                it.list.forEach { card -> checkMediaCard(card = card, cardType = config.cardType) }
                addAll(it.list)
            }
        }
        val result = service.catalog(options = options, loadKey = "1", loadSize = 20)
        check(result.prevKey == null)
        check(result.nextKey == "2")
        result.list.forEach { card -> checkMediaCard(card = card, cardType = config.cardType) }
        check(result.list.isNotEmpty())
        combineList.forEachIndexed { index, card ->
            val otherCard = result.list[index]
            check(card.id == otherCard.id)
        }
    }
}