package com.jzbrooks.readlater.common

import com.jzbrooks.readlater.common.Entry
import com.jzbrooks.readlater.common.db.Database
import com.jzbrooks.readlater.common.db.DriverFactory
import com.jzbrooks.readlater.common.net.auth.AuthenticationManager
import com.jzbrooks.readlater.common.net.entries.EntryService
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow

class CachingEntryRepository(
    val authenticationManager: AuthenticationManager,
    private val database: Database,
    private val service: EntryService,
) : EntryRepository {

    @Suppress("unused") // Used for Swift
    constructor(
        authenticationManager: AuthenticationManager,
        databaseDriver: DriverFactory,
        service: EntryService
    ) : this(
        authenticationManager,
        Database(databaseDriver.createDriver()),
        service,
    )

    override val entries: Flow<List<Entry>>
        get() = database.entryQueries.selectAll().asFlow().mapToList()

    @Suppress("unused") // Used for Swift
    suspend fun entries(): List<Entry> = database.entryQueries.selectAll().executeAsList()

    override suspend fun updateEntries() {
        val entries = service.getEntries()
        for (entry in entries) {
            database.entryQueries.insertEntry(
                entry.id,
                entry.title,
                entry.url,
                entry.content,
                entry.previewPicture,
            )
        }
    }

    override suspend fun getEntry(id: Long): Entry? {
        return database.entryQueries.getEntry(id).executeAsOneOrNull()
    }

    override suspend fun deleteAllEntries() {
        database.entryQueries.deleteAll()
    }
}
