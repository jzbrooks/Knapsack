package com.jzbrooks.readlater.common.net.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshGrantRequestDto(
    @SerialName("client_id") val clientId: String,
    @SerialName("client_secret") val clientSecret: String,
    @SerialName("refresh_token") val refreshToken: String,
    @SerialName("grant_type") val grantType: String = "refresh_token",
)
