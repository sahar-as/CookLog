package com.saharapps.common.model

data class RecipeItem(
    val id: Long = 0,
    val name: String,
    val explanation: String,
    val images: List<ByteArray>?,
    val cookTime: Int? = 0,
    val isFavorite: Boolean = false,
    val catalogId: Long,
)