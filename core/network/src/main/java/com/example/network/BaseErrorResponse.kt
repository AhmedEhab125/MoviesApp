package com.example.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class BaseErrorResponse(
    @SerialName("status_code")
    val statusCode: Int?,
    @SerialName("status_message")
    val statusMessage: String?
)
