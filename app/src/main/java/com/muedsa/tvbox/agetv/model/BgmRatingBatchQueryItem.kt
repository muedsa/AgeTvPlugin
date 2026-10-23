package com.muedsa.tvbox.agetv.model

import kotlinx.serialization.Serializable

@Serializable
data class BgmRatingBatchQueryItem(
    val inputId: String,
    val names: List<String>,
)
