package com.jzbrooks.readlater.data.net.auth

import com.jzbrooks.readlater.data.AppSettings
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class AuthenticationService(private val appSettings: AppSettings) : AuthService {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
            })
        }
    }

    override suspend fun authenticate(requestDto: PasswordGrantRequestDto): GrantResponseDto {
        return authenticate<PasswordGrantRequestDto>(requestDto)
    }

    override suspend fun authenticate(requestDto: RefreshGrantRequestDto): GrantResponseDto {
        return authenticate<RefreshGrantRequestDto>(requestDto)
    }

    private suspend inline fun <reified T> authenticate(requestDto: T) : GrantResponseDto {
        return withContext(Dispatchers.Default) {
            val url = URLBuilder(appSettings.baseUrl)
                .appendPathSegments("oauth", "v2", "token")
                .build()

            val response = httpClient.post(url) {
                contentType(ContentType("application", "json"))
                setBody(requestDto)
            }

            if (!response.status.isSuccess()) {
                println("Auth request failed with $response\n\t${response.bodyAsText()}")
            }

            response.body()
        }
    }
}