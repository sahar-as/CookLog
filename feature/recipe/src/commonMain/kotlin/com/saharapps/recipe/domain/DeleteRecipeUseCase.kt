package com.saharapps.recipe.domain

interface DeleteRecipeUseCase {
    suspend operator fun invoke(recipeId: Long)
}