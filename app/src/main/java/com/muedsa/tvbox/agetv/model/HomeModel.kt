package com.muedsa.tvbox.agetv.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeModel(
    @SerialName("latest")
    val latest: List<PosterAnimeModel>,
    @SerialName("recommend")
    val recommend: List<PosterAnimeModel>,
    @SerialName("week_list")
    val weekList: Map<String, List<WeekAnimeModel>>
)
