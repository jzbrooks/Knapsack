package com.jzbrooks.readlater.data.net.auth

interface AuthService {
    suspend fun authenticate(requestDto: PasswordGrantRequestDto): GrantResponseDto
    suspend fun authenticate(requestDto: RefreshGrantRequestDto): GrantResponseDto
}