package com.jzbrooks.readlater.common

import kotlinx.coroutines.flow.Flow

interface EntryRepository {
    val entries: Flow<List<Entry>>
    suspend fun updateEntries()
    suspend fun deleteAllEntries()
    suspend fun getEntry(id: Int): Entry?
}
