package com.jzbrooks.readlater.data

interface AppSettings {
    val baseUrl: String

    object Keys {
        const val BASE_URL = "burl"
    }

    object Defaults {
        const val BASE_URL = "https://app.wallabag.it"
    }
}