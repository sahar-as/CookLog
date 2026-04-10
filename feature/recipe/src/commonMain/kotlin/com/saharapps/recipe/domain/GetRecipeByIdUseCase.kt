package com.saharapps.recipe.domain

import com.saharapps.common.model.RecipeItem

interface GetRecipeByIdUseCase {
    suspend operator fun invoke(id: Long): Result<RecipeItem>
}