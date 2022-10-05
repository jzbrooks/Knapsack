package com.jzbrooks.readlater.data.net.auth

interface AuthenticationManager {
    val isAuthenticated: Boolean
    suspend fun retrieveAccessToken(): String
    suspend fun authenticate(password: PasswordGrantRequestDto)
    fun deleteCredentials()
}