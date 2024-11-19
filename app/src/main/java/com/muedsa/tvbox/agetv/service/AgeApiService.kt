package com.muedsa.tvbox.agetv.service

import com.muedsa.tvbox.agetv.model.AnimeDetailPageModel
import com.muedsa.tvbox.agetv.model.CatalogAnimeModel
import com.muedsa.tvbox.agetv.model.HomeModel
import com.muedsa.tvbox.agetv.model.HorizontalPosterAnimeModel
import com.muedsa.tvbox.agetv.model.PagedVideosModel
import com.muedsa.tvbox.agetv.model.PosterAnimeModel
import com.muedsa.tvbox.agetv.model.RespModel
import com.muedsa.tvbox.agetv.model.YearRankModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface AgeApiService {

    @GET("v2/home-list")
    suspend fun homeList(): HomeModel

        @GET("v2/slipic")
    suspend fun slipic(): List<HorizontalPosterAnimeModel>

    @GET("v2/recommend")
    suspend fun recommend(): PagedVideosModel<PosterAnimeModel>

    @GET("v2/catalog")
    suspend fun catalog(
        @QueryMap queryMap: Map<String, String> = mapOf(
            "region" to "all",
            "genre" to "all",
            "letter" to "all",
            "year" to "all",
            "season" to "all",
            "status" to "all",
            "label" to "all",
            "resource" to "all",
            "order" to "time",
        ),
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10,
    ): PagedVideosModel<CatalogAnimeModel>

    @GET("v2/search")
    suspend fun search(
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): RespModel<PagedVideosModel<CatalogAnimeModel>>

    @GET("v2/update")
    suspend fun update(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 30
    ): PagedVideosModel<PosterAnimeModel>

    @GET("v2/rank")
    suspend fun rank(@Query("year") year: String = ""): YearRankModel

    @GET("v2/detail/{aid}")
    suspend fun detail(@Path("aid") aid: Long): AnimeDetailPageModel

}