package com.saharapps.recipe.domain

import com.saharapps.common.model.RecipeItem

interface SaveRecipeUseCase {
    suspend operator fun invoke(recipe: RecipeItem)
}