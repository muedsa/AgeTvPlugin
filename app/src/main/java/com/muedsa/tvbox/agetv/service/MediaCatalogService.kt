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
    private val bgmRatingQueryService: BgmRatingQueryService,
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
        val results = BgmRatingQueryService.loopCatalogBatchQuery(
            medias = resp.videos,
            service = bgmRatingQueryService,
        )
        return PagingResult(
            list = resp.videos.map {
                val id = it.id.toString()
                val result = results[id]
                var subTitle = it.status
                if (result?.rating?.score != null) {
                    subTitle = "$subTitle ★${result.rating.score}"
                }
                MediaCard(
                    id = id,
                    title = it.name,
                    subTitle = subTitle,
                    coverImageUrl = it.cover,
                    detailUrl = id
                )
            },
            prevKey = if (page > 1) "${page - 1}" else null,
            nextKey = if (page * loadSize < resp.total) "${page + 1}" else null
        )
    }
}