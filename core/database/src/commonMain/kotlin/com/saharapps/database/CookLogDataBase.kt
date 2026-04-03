package com.saharapps.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saharapps.database.catalog.CatalogDao
import com.saharapps.database.catalog.CatalogEntity

@Database(
    entities = [CatalogEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun catalogDao(): CatalogDao
}

expect fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>

fun createDatabase(): AppDatabase {
    return getDatabaseBuilder()
        .fallbackToDestructiveMigration(dropAllTables = true)
        .build()
}