package com.jzbrooks.readlater.data.net.entries

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EntriesDto(@SerialName("_embedded") val entries: ItemDto)