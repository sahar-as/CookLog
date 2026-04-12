package com.saharapps.recipe.data

import com.saharapps.database.recipe.RecipeDao
import com.saharapps.database.recipe.RecipeEntity

internal class RecipeRepositoryImpl(
    private val recipeDao: RecipeDao
): RecipeRepository {
    override suspend fun getRecipeById(id: Long): Result<RecipeEntity> {
        return Result.success(recipeDao.getRecipe(id))
    }

    override suspend fun saveRecipe(recipeEntity: RecipeEntity) {
        recipeDao.insertRecipe(recipeEntity)
    }
    override suspend fun updateFavoriteStatus(
        recipeId: Long,
        isFavorite: Boolean
    ) {
        recipeDao.updateFavoriteStatus(
            recipeId = recipeId,
            isFavorite = isFavorite
        )
    }
}