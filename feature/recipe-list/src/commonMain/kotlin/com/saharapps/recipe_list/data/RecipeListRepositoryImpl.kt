package com.saharapps.recipe_list.data

import com.saharapps.database.recipe.RecipeDao
import com.saharapps.database.recipe.RecipeEntity

internal class RecipeListRepositoryImpl(
    private val recipeDao: RecipeDao
) : RecipeListRepository {
    override suspend fun getRecipesByCatalog(catalogId: Long): Result<List<RecipeEntity>> {
        return Result.success(recipeDao.getRecipesByCatalog(catalogId))
    }

    override suspend fun deleteRecipe(recipeId: Long) {
        recipeDao.deleteRecipesById(recipeId)
    }
}