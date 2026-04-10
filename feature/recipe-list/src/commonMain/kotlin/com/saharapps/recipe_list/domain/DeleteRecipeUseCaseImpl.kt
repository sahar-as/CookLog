package com.saharapps.recipe_list.domain

import com.saharapps.recipe_list.data.RecipeListRepository

internal class DeleteRecipeUseCaseImpl(
    private val recipeListRepository: RecipeListRepository
) : DeleteRecipeUseCase {
    override suspend fun invoke(recipeId: Long) {
        recipeListRepository.deleteRecipe(recipeId)
    }
}