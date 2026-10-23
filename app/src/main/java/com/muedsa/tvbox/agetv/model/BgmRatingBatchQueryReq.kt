package com.muedsa.tvbox.agetv.model

import kotlinx.serialization.Serializable

@Serializable
data class BgmRatingBatchQueryReq(
    val items: List<BgmRatingBatchQueryItem>
)
