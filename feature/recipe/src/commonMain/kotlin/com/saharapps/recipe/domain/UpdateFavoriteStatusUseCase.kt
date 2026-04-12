package com.saharapps.recipe.domain

interface UpdateFavoriteStatusUseCase {
    suspend operator fun invoke(
        recipeId: Long,
        isFavorite: Boolean
    )
}