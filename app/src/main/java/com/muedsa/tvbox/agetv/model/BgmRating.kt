package com.muedsa.tvbox.agetv.model

import kotlinx.serialization.Serializable

@Serializable
data class BgmRating(
    val score: Float? = null,
    val votes: Int? = null,
    val rank: Int? = null,
    val source: String? = null,
)
