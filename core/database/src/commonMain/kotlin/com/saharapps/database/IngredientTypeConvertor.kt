package com.saharapps.database

import com.saharapps.common.model.Ingredient
import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class IngredientConvertor {
    @TypeConverter
    fun fromIngredientList(value: List<Ingredient>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toIngredientList(value: String): List<Ingredient> {
        return try {
            Json.decodeFromString(value)
        } catch (e: Exception) {
            emptyList()
        }
    }
}