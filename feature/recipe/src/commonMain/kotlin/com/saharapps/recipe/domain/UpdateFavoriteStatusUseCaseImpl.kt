package com.saharapps.recipe.domain

import com.saharapps.recipe.data.RecipeRepository


class UpdateFavoriteStatusUseCaseImpl(
    private val recipeListRepository: RecipeRepository
) : UpdateFavoriteStatusUseCase {
    override suspend fun invoke(
        recipeId: Long,
        isFavorite: Boolean
    ) {
        recipeListRepository.updateFavoriteStatus(
            recipeId = recipeId,
            isFavorite = isFavorite
        )
    }
}