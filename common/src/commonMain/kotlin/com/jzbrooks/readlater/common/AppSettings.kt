package com.jzbrooks.readlater.common

interface AppSettings {
    val baseUrl: String

    object Keys {
        const val BASE_URL = "burl"
    }

    object Defaults {
        const val BASE_URL = "https://app.wallabag.it"
    }
}
