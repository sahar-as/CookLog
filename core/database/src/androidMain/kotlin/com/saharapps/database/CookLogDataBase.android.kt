package com.saharapps.database

import android.content.Context
import androidx.room.Room

actual class DatabaseFactory(
    private val context: Context
) {
    actual fun create(): CookLogDatabase {
        return Room.databaseBuilder(
            context,
            CookLogDatabase::class.java,
            "cooklog.db"
        ).build()
    }
}
