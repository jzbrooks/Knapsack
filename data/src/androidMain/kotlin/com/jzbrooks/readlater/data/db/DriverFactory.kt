package com.jzbrooks.readlater.data.db

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            com.jzbrooks.readlater.data.Database.Schema,
            context,
            "readlater.db"
        )
    }
}