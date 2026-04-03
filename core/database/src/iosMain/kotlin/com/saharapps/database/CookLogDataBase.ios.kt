package com.saharapps.database

import androidx.room.Room
import androidx.room.RoomDatabase
import platform.Foundation.NSHomeDirectory

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbPath = NSHomeDirectory() + "/cooklog.db"

    return Room.databaseBuilder<AppDatabase>(
        name = dbPath
    )
}