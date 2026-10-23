package com.muedsa.tvbox.agetv.service

import com.muedsa.tvbox.agetv.CardHeight
import com.muedsa.tvbox.agetv.CardWidth
import com.muedsa.tvbox.api.data.MediaCard
import com.muedsa.tvbox.api.data.MediaCardRow
import com.muedsa.tvbox.api.service.IMediaSearchService

class MediaSearchService(
    private val ageApiService: AgeApiService,
    private val bgmRatingQueryService: BgmRatingQueryService,
) : IMediaSearchService {
    override suspend fun searchMedias(query: String): MediaCardRow {
        val resp = ageApiService.search(query = query)
        check(resp.code == 200) { resp.message ?: "搜索失败" }
        val list = resp.data?.videos ?: emptyList()
        val results = BgmRatingQueryService.loopCatalogBatchQuery(
            medias = list,
            service = bgmRatingQueryService,
        )
        return MediaCardRow(
            title = "search list",
            cardWidth = CardWidth,
            cardHeight = CardHeight,
            list = list.map {
                val id = it.id.toString()
                val result = results[id]
                var subTitle = it.status
                if (result?.rating?.score != null) {
                    subTitle = "$subTitle ★${result.rating.score}"
                }
                MediaCard(
                    id = id,
                    title = it.name,
                    detailUrl = id,
                    coverImageUrl = it.cover,
                    subTitle = subTitle
                )
            }
        )
    }
}