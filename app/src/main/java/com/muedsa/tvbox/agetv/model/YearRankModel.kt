package com.muedsa.tvbox.agetv.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class YearRankModel(
    @SerialName("rank")
    val rank: List<List<RankAnimeModel>>,
    @SerialName("total")
    val total: Int,
    @SerialName("year")
    val year: String
)