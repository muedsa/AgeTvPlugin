package com.muedsa.tvbox.agetv.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PageInfoModel(
    @SerialName("curPage")
    val curPage: Int,
    @SerialName("pageCount")
    val pageCount: Int,
    @SerialName("pageNoList")
    val pageNoList: List<Int>,
    @SerialName("total")
    val total: Int,
    @SerialName("totalPage")
    val totalPage: Int,
)