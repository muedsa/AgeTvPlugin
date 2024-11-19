package com.muedsa.tvbox.agetv.model

import kotlinx.serialization.Serializable

@Serializable
data class AgeFilterOptionData(
    val text: String,
    val value: String
)