package com.jzbrooks.readlater.common.net.entries

import com.jzbrooks.readlater.common.AppSettings
import com.jzbrooks.readlater.common.net.auth.AuthenticationManager
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.URLBuilder
import io.ktor.http.appendEncodedPathSegments
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class EntryService(
    private val appSettings: AppSettings,
    private val authenticationManager: AuthenticationManager,
) {
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

    suspend fun getEntries(): List<EntryDto> {
        return withContext(Dispatchers.Default) {
            val url = URLBuilder(appSettings.baseUrl)
                .appendEncodedPathSegments("api", "entries.json")
                .build()

            val response = httpClient.get(url) {
                header("Authorization", "Bearer ${authenticationManager.retrieveAccessToken()}")
                contentType(ContentType("application", "json"))
            }

            if (response.status.isSuccess()) {
                println("Request failure $response\n\t${response.bodyAsText()})")
            }

            response.body<EntriesDto>().entries.items
        }
    }
}
