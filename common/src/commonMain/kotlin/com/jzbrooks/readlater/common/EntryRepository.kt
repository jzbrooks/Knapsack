package com.jzbrooks.readlater.common

import com.jzbrooks.readlater.common.Entry
import kotlinx.coroutines.flow.Flow

interface EntryRepository {
    val entries: Flow<List<Entry>>
    suspend fun updateEntries()
    suspend fun deleteAllEntries()
    suspend fun getEntry(id: Long): Entry?
}
