package com.saharapps.database

import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.mp.KoinPlatform

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val context = KoinPlatform.getKoin().get<android.content.Context>()
    return Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "cooklog.db"
    )
}
