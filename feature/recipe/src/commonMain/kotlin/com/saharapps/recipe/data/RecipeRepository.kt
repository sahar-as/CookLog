package com.saharapps.recipe.data

import com.saharapps.database.recipe.RecipeEntity

interface RecipeRepository {
    suspend fun getRecipeById(id: Long): Result<RecipeEntity>
    suspend fun saveRecipe(recipeEntity: RecipeEntity)
}