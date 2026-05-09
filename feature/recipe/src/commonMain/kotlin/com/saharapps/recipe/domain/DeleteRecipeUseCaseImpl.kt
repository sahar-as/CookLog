package com.saharapps.recipe.domain

import com.saharapps.recipe.data.RecipeRepository


internal class DeleteRecipeUseCaseImpl(
    private val recipeListRepository: RecipeRepository
) : DeleteRecipeUseCase {
    override suspend fun invoke(recipeId: Long) {
        recipeListRepository.deleteRecipe(recipeId)
    }
}