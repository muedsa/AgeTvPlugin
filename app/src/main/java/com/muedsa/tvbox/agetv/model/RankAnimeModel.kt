package com.muedsa.tvbox.agetv.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RankAnimeModel(
    @SerialName("NO")
    val no: Int,
    @SerialName("AID")
    val aid: Long,
    @SerialName("Title")
    val title: String,
    @SerialName("CCnt")
    val ccNt: String,
)
