package com.jzbrooks.readlater.common.net.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GrantResponseDto(
    @SerialName("access_token") val accessToken: String,
    @SerialName("expires_in") val expiresIn: Int,
    @SerialName("token_type") val tokenType: String,
    @SerialName("refresh_token") val refreshToken: String,
    val scope: String?,
)
