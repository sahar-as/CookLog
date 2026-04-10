package com.saharapps.recipe_list.domain.model

import com.saharapps.common.model.CookLogImage

data class RecipeItem(
    val id: Long = 0,
    val name: String,
    val explanation: String,
    val image: CookLogImage,
    val isFavorite: Boolean = false,
    val catalogId: Long,
)