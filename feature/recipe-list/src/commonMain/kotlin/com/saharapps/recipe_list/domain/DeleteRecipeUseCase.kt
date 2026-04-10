package com.saharapps.recipe_list.domain

interface DeleteRecipeUseCase {
    suspend operator fun invoke(recipeId: Long)
}