package com.saharapps.database

import androidx.room.TypeConverter
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class ByteArrayListConverter {

    @OptIn(ExperimentalEncodingApi::class)
    @TypeConverter
    fun fromList(list: List<ByteArray>): String {
        return list.joinToString("###") {
            Base64.encode(it)
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    @TypeConverter
    fun toList(data: String): List<ByteArray> {
        if (data.isBlank()) return emptyList()

        return data.split("###").map {
            Base64.decode(it)
        }
    }
}