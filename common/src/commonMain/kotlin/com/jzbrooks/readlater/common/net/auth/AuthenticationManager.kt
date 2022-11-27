package com.jzbrooks.readlater.common.net.auth

interface AuthenticationManager {
    val isAuthenticated: Boolean
    suspend fun retrieveAccessToken(): String
    suspend fun authenticate(password: PasswordGrantRequestDto)
    fun deleteCredentials()
}
