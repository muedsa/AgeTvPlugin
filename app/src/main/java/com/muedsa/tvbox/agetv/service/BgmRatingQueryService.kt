package com.muedsa.tvbox.agetv.service

import com.muedsa.tvbox.agetv.model.BgmRatingBatchQueryItem
import com.muedsa.tvbox.agetv.model.BgmRatingBatchQueryReq
import com.muedsa.tvbox.agetv.model.BgmRatingBatchQueryResp
import com.muedsa.tvbox.agetv.model.BgmRatingBatchQueryResult
import com.muedsa.tvbox.agetv.model.CatalogAnimeModel
import com.muedsa.tvbox.agetv.model.PosterAnimeModel
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface BgmRatingQueryService {

    @POST("api/v1/ratings/batch")
    @Headers("User-Agent: muedsa/AgeTvPlugin (https://github.com/muedsa/AgeTvPlugin)")
    suspend fun batchQuery(@Body req: BgmRatingBatchQueryReq): BgmRatingBatchQueryResp

    companion object {

        const val MAX_BATCH_QUERY_ITEM_NUM = 20

        suspend fun loopPosterBatchQuery(
            medias: List<PosterAnimeModel>,
            service: BgmRatingQueryService
        ): Map<String, BgmRatingBatchQueryResult> {
            if (medias.isEmpty()) {
                return emptyMap()
            }
            val chunkedItems = medias
                .map {
                    BgmRatingBatchQueryItem(
                        inputId = "${it.aid}",
                        names = listOf(it.title)
                    )
                }.distinctBy { it.inputId }
                .chunked(MAX_BATCH_QUERY_ITEM_NUM)
            return chunkedItems.map { items ->
                try {
                    val resp = service.batchQuery(
                        req = BgmRatingBatchQueryReq(items = items)
                    )
                    return resp.results?.filter {
                        it.status == "matched" && it.rating != null
                    }?.associateBy { it.inputId } ?: emptyMap()
                } catch (_: Throwable) {
                    emptyMap<String, BgmRatingBatchQueryResult>()
                }
            }.flatMap { it.entries }
                .associate { it.key to it.value }
        }

        suspend fun loopCatalogBatchQuery(
            medias: List<CatalogAnimeModel>,
            service: BgmRatingQueryService
        ): Map<String, BgmRatingBatchQueryResult> {
            if (medias.isEmpty()) {
                return emptyMap()
            }
            val chunkedItems = medias
                .map {
                    BgmRatingBatchQueryItem(
                        inputId = "${it.id}",
                        names = listOf(it.name)
                    )
                }.distinctBy { it.inputId }
                .chunked(MAX_BATCH_QUERY_ITEM_NUM)
            return chunkedItems.map { items ->
                try {
                    val resp = service.batchQuery(
                        req = BgmRatingBatchQueryReq(items = items)
                    )
                    return resp.results?.filter {
                        it.status == "matched" && it.rating != null
                    }?.associateBy { it.inputId } ?: emptyMap()
                } catch (_: Throwable) {
                    emptyMap<String, BgmRatingBatchQueryResult>()
                }
            }.flatMap { it.entries }
                .associate { it.key to it.value }
        }
    }
}