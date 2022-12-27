package com.jzbrooks.readlater.common.net.entries

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
@JsExport
class ItemDto(val items: Array<EntryDto>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ItemDto

        if (!items.contentEquals(other.items)) return false

        return true
    }

    override fun hashCode(): Int {
        return items.contentHashCode()
    }
}
