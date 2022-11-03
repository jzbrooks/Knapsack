package com.jzbrooks.readlater.data

import com.russhwolf.settings.Settings
import io.ktor.http.*

class SettingsManager(private val settings: Settings) : AppSettingsManager {
    constructor(): this(Settings())

    override var baseUrl: String
        get() = settings.getString(AppSettings.Keys.BASE_URL, AppSettings.Defaults.BASE_URL)
        set(value) {
            try {
                Url(value)
                settings.putString(AppSettings.Keys.BASE_URL, value)
            } catch (e: URLParserException) {
                println("Unable to parse url $value")
            }
        }
}
