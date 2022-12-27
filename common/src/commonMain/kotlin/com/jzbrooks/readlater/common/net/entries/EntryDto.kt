package com.jzbrooks.readlater.common.net.entries

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EntryDto(
    val id: Int,
    val title: String,
    val url: String,
    val content: String,
    @SerialName("preview_picture") val previewPicture: String? = null,
)
