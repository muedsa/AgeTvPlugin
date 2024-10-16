package com.muedsa.tvbox.agetv.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RespModel<E>(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String?,
    @SerialName("data")
    val data: E? = null
)