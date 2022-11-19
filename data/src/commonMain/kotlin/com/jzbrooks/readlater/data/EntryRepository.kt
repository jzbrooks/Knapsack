package com.jzbrooks.readlater.data

import com.jzbrooks.readlater.data.Entry
import kotlinx.coroutines.flow.Flow

interface EntryRepository {
    val entries: Flow<List<Entry>>
    suspend fun updateEntries()
    suspend fun deleteAllEntries()
    suspend fun getEntry(id: Long): Entry?
}
