package com.saharapps.database

import androidx.room.TypeConverter

class StringListConverter {
    @TypeConverter
    fun fromList(list: List<String>?): String? {
        return list?.joinToString(separator = "|")
    }

    @TypeConverter
    fun toList(data: String?): List<String>? {
        if (data.isNullOrBlank()) return emptyList()

        return data.split("|")
    }
}