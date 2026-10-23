package com.muedsa.tvbox.agetv.model

import kotlinx.serialization.Serializable

@Serializable
data class BgmRatingBatchQueryResp(
    val results: List<BgmRatingBatchQueryResult>? = null,
    val error: BgmRatingQueryError? = null,
)
