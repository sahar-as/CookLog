package com.saharapps.database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import platform.Foundation.NSHomeDirectory

actual class DatabaseFactory {
    actual fun create(): CookLogDatabase {
        val dbFilePath = NSHomeDirectory() + "/cooklog.db"

        return Room.databaseBuilder<CookLogDatabase>(
            name = dbFilePath,
            factory = { CookLogDatabaseConstructor.initialize() }
        )
            .setDriver(BundledSQLiteDriver())
            .build()
    }
}