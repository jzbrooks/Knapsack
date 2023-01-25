package com.jzbrooks.readlater.common.net.auth

interface AuthService {
    suspend fun authenticate(requestDto: PasswordGrantRequestDto): Result<GrantResponseDto>
    suspend fun authenticate(requestDto: RefreshGrantRequestDto): Result<GrantResponseDto>
}
