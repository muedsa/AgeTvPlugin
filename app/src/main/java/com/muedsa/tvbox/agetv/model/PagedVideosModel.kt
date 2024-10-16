package com.muedsa.tvbox.agetv.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagedVideosModel<E>(
    @SerialName("total")
    val total: Int,
    @SerialName("totalPage")
    val totalPage: Int = -1,
    @SerialName("videos")
    val videos: List<E>
)