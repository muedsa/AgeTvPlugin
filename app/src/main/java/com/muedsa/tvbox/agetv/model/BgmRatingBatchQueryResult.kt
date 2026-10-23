package com.muedsa.tvbox.agetv.model

import kotlinx.serialization.Serializable

@Serializable
data class BgmRatingBatchQueryResult(
    val inputId: String = "",
    val status: String = "",
    val subject: BgmRatingBatchQuerySubject? = null,
    val matchReason: String? = null,
    val matchedAliases: List<BgmRatingBatchQuerySubjectAlias>? = null,
    val rating: BgmRating? = null,
    val ratingStatus: String? = null,
    val dataAsOf: String? = null,
    val stale: Boolean? = null,
)
