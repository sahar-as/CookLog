package com.saharapps.recipe_list.domain

interface UpdateFavoriteStatusUseCase {
    suspend operator fun invoke(
        recipeId: Long,
        isFavorite: Boolean
    )
}