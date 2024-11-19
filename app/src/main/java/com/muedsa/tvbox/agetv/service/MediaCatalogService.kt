package com.muedsa.tvbox.agetv.service

import com.muedsa.tvbox.agetv.AgeCatalogOptions
import com.muedsa.tvbox.agetv.CardHeight
import com.muedsa.tvbox.agetv.CardWidth
import com.muedsa.tvbox.api.data.MediaCard
import com.muedsa.tvbox.api.data.MediaCardType
import com.muedsa.tvbox.api.data.MediaCatalogConfig
import com.muedsa.tvbox.api.data.MediaCatalogOption
import com.muedsa.tvbox.api.data.PagingResult
import com.muedsa.tvbox.api.service.IMediaCatalogService

class MediaCatalogService(
    private val ageApiService: AgeApiService,
) : IMediaCatalogService {

    override suspend fun getConfig(): MediaCatalogConfig =
        MediaCatalogConfig(
            initKey = "1",
            pageSize = 10,
            cardWidth = CardWidth,
            cardHeight = CardHeight,
            cardType = MediaCardType.STANDARD,
            catalogOptions = AgeCatalogOptions
        )

    override suspend fun catalog(
        options: List<MediaCatalogOption>,
        loadKey: String,
        loadSize: Int
    ): PagingResult<MediaCard> {
        val page = loadKey.toInt()
        val queryMap = mutableMapOf<String, String>()
        options.forEach { queryMap[it.value] = it.items[0].value }
        val resp = ageApiService.catalog(
            queryMap = queryMap,
            page = page,
            size = loadSize
        )
        return PagingResult(
            list = resp.videos.map {
                val mediaId = it.id.toString()
                MediaCard(
                    id = mediaId,
                    title = it.name,
                    subTitle = it.status,
                    coverImageUrl = it.cover,
                    detailUrl = mediaId
                )
            },
            prevKey = if (page > 1) "${page - 1}" else null,
            nextKey = if (page * loadSize < resp.total) "${page + 1}" else null
        )
    }
}