package com.muedsa.tvbox.agetv.model

import kotlinx.serialization.Serializable

@Serializable
data class AgeFilterOption(
    val name: String,
    val label: String,
    val data: List<AgeFilterOptionData>
)