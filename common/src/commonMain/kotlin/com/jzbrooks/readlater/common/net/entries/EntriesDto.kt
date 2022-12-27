package com.jzbrooks.readlater.common.net.entries

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
@JsExport
data class EntriesDto(@SerialName("_embedded") val entries: ItemDto)
