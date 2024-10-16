package com.muedsa.tvbox.agetv.service

import com.muedsa.tvbox.agetv.CardHeight
import com.muedsa.tvbox.agetv.CardWidth
import com.muedsa.tvbox.api.data.MediaCard
import com.muedsa.tvbox.api.data.MediaCardRow
import com.muedsa.tvbox.api.service.IMediaSearchService

class MediaSearchService(
    private val ageApiService: AgeApiService
) : IMediaSearchService {
    override suspend fun searchMedias(query: String): MediaCardRow {
        val resp = ageApiService.search(query = query)
        check(resp.code == 200) { resp.message ?: "搜索失败" }
        val list = resp.data?.videos ?: emptyList()
        return MediaCardRow(
            title = "search list",
            cardWidth = CardWidth,
            cardHeight = CardHeight,
            list = list.map {
                MediaCard(
                    id = it.id.toString(),
                    title = it.name,
                    detailUrl = it.id.toString(),
                    coverImageUrl = it.cover,
                    subTitle = it.status
                )
            }
        )
    }
}