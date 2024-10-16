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

interface AgeApiService {

    @GET("v2/home-list")
    suspend fun homeList(): HomeModel

        @GET("v2/slipic")
    suspend fun slipic(): List<HorizontalPosterAnimeModel>

    @GET("v2/recommend")
    suspend fun recommend(): PagedVideosModel<PosterAnimeModel>

    @GET("v2/catalog")
    suspend fun catalog(
        @Query("genre") genre: String = "all",
        @Query("label") label: String = "all",
        @Query("letter") letter: String = "all",
        @Query("order") order: String = "time",
        @Query("region") region: String = "all",
        @Query("resource") resource: String = "all",
        @Query("season") season: String = "all",
        @Query("status") status: String = "all",
        @Query("year") year: String = "all",
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