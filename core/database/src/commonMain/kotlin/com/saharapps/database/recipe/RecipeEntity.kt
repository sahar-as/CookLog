package com.saharapps.database.recipe

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_table")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val explanation: String,
    val image: ByteArray?,
    val resourceIndex: Int?,
    val isFavorite: Boolean,
    val catalogId: Long,
)