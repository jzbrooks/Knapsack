package com.jzbrooks.readlater.common.net.auth

import com.jzbrooks.readlater.common.AppSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.toInstant
import kotlin.time.Duration.Companion.seconds

class Authenticator(
    private val authService: AuthService,
    private val settings: Settings,
) : AuthenticationManager {

    constructor(appSettings: AppSettings) : this(AuthenticationService(appSettings), Settings())

    override val isAuthenticated: Boolean
        get() = settings.getStringOrNull(Keys.ACCESS_TOKEN) != null

    private var clientId: String?
        get() = settings.getStringOrNull(Keys.CLIENT_ID)
        set(value) { settings[Keys.CLIENT_ID] = value }

    private var clientSecret: String?
        get() = settings.getStringOrNull(Keys.CLIENT_SECRET)
        set(value) { settings[Keys.CLIENT_SECRET] = value }

    private var accessToken: String?
        get() = settings.getStringOrNull(Keys.ACCESS_TOKEN)
        set(value) { settings[Keys.ACCESS_TOKEN] = value }

    private var refreshToken: String?
        get() = settings.getStringOrNull(Keys.REFRESH_TOKEN)
        set(value) { settings[Keys.REFRESH_TOKEN] = value }

    private var expiration: Instant?
        get() = settings.getStringOrNull(Keys.EXPIRES)?.toInstant()
        set(value) { settings[Keys.EXPIRES] = value.toString() }

    override suspend fun retrieveAccessToken(): String {
        if (isAuthenticated && expiration!! < Clock.System.now()) {
            refreshAccessToken()
        }

        return checkNotNull(accessToken) { "Access tokens are only retrievable if authenticated." }
    }

    override suspend fun authenticate(password: PasswordGrantRequestDto) {
        val grant = authService.authenticate(password) ?: return
        clientId = password.clientId
        clientSecret = password.clientSecret
        accessToken = grant.accessToken
        refreshToken = grant.refreshToken

        // It might be more precise to look for a header with a start time in it?
        expiration = Clock.System.now() + grant.expiresIn.seconds
    }

    override fun deleteCredentials() {
        settings.remove(Keys.ACCESS_TOKEN)
        settings.remove(Keys.REFRESH_TOKEN)
        settings.remove(Keys.EXPIRES)
        settings.remove(Keys.CLIENT_ID)
        settings.remove(Keys.CLIENT_SECRET)
    }

    private suspend fun refreshAccessToken() {
        val refresh = RefreshGrantRequestDto(clientId!!, clientSecret!!, refreshToken!!)
        val grant = authService.authenticate(refresh) ?: return
        accessToken = grant.accessToken
        refreshToken = grant.refreshToken

        // It might be more precise to look for a header with a start time in it?
        expiration = Clock.System.now() + grant.expiresIn.seconds
    }

    private object Keys {
        const val ACCESS_TOKEN = "at"
        const val REFRESH_TOKEN = "rt"
        const val CLIENT_ID = "cid"
        const val CLIENT_SECRET = "cs"
        const val EXPIRES = "exp"
    }
}
