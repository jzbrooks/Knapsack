package com.jzbrooks.readlater.data.net.entries

import kotlinx.serialization.Serializable

@Serializable
data class ItemDto(val items: List<EntryDto>)