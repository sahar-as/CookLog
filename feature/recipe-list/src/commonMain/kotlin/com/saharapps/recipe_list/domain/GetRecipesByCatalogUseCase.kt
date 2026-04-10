package com.saharapps.recipe_list.domain

import com.saharapps.recipe_list.domain.model.RecipeItem

interface GetRecipesByCatalogUseCase {
    suspend operator fun invoke(catalogId: Long): Result<List<RecipeItem>>
}