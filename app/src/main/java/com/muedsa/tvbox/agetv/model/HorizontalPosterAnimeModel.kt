package com.muedsa.tvbox.agetv.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HorizontalPosterAnimeModel(
    @SerialName("AID")
    val aid: Long,
    @SerialName("Title")
    val title: String,
    @SerialName("PicUrl")
    val picUrl: String
)