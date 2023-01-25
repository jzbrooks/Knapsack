package com.jzbrooks.readlater.common.net.auth

import com.jzbrooks.readlater.common.AppSettings
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.URLBuilder
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class AuthenticationService(private val appSettings: AppSettings) : AuthService {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                },
            )
        }
    }

    override suspend fun authenticate(
        requestDto: PasswordGrantRequestDto,
    ): Result<GrantResponseDto> {
        return authenticate<PasswordGrantRequestDto>(requestDto)
    }

    override suspend fun authenticate(
        requestDto: RefreshGrantRequestDto,
    ): Result<GrantResponseDto> {
        return authenticate<RefreshGrantRequestDto>(requestDto)
    }

    private suspend inline fun <reified T> authenticate(
        requestDto: T,
    ): Result<GrantResponseDto> {
        return withContext(Dispatchers.Default) {
            val url = URLBuilder(appSettings.baseUrl)
                .appendPathSegments("oauth", "v2", "token")
                .build()

            try {
                val response = httpClient.post(url) {
                    contentType(ContentType("application", "json"))
                    setBody(requestDto)
                }

                if (response.status.isSuccess()) {
                    Result.success(response.body())
                } else {
                    Result.failure(
                        IllegalStateException(
                            "Auth request failed with $response (${response.bodyAsText()})",
                        ),
                    )
                }
            } catch (e: IOException) {
                Result.failure(e)
            }
        }
    }
}
