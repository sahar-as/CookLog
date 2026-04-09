package com.saharapps.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.saharapps.database.catalog.CatalogDao
import com.saharapps.database.catalog.CatalogEntity

@Database(
    entities = [CatalogEntity::class],
    version = 1
)
@ConstructedBy(CookLogDatabaseConstructor::class)
abstract class CookLogDatabase : RoomDatabase() {
    abstract fun catalogDao(): CatalogDao
}

expect class DatabaseFactory {
    fun create(): CookLogDatabase
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object CookLogDatabaseConstructor : RoomDatabaseConstructor<CookLogDatabase>