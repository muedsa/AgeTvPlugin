package com.muedsa.tvbox.agetv.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeekAnimeModel(
    @SerialName("id")
    val id: Long,
    @SerialName("isnew")
    val isNew: Int, // 0否 1是
    @SerialName("mtime")
    val mTime: String,
    @SerialName("name")
    val name: String,
    @SerialName("namefornew")
    val nameForNew: String,
    @SerialName("wd")
    val wd: Int,
)
