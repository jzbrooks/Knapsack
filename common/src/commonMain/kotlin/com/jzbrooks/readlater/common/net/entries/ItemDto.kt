package com.jzbrooks.readlater.common.net.entries

import kotlinx.serialization.Serializable

@Serializable
data class ItemDto(val items: List<EntryDto>)
