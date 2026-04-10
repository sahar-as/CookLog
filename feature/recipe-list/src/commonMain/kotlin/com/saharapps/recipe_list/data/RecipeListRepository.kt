package com.saharapps.recipe_list.data

import com.saharapps.database.recipe.RecipeEntity

interface RecipeListRepository {
    suspend fun getRecipesByCatalog(catalogId: Long): Result<List<RecipeEntity>>
}