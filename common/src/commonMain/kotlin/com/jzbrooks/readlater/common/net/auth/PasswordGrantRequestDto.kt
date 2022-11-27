package com.jzbrooks.readlater.common.net.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PasswordGrantRequestDto constructor(
    @SerialName("client_id") val clientId: String,
    @SerialName("client_secret") val clientSecret: String,
    val username: String,
    val password: String,
    @SerialName("grant_type") val grantType: String = "password",
)
