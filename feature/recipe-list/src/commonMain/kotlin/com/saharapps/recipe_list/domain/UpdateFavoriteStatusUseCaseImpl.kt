package com.saharapps.recipe_list.domain

import com.saharapps.recipe_list.data.RecipeListRepository

class UpdateFavoriteStatusUseCaseImpl(
    private val recipeListRepository: RecipeListRepository
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