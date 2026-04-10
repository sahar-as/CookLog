package com.saharapps.database.recipe

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe_table WHERE id = :recipeId")
    suspend fun getRecipe(recipeId: Long): RecipeEntity

    @Query("SELECT * FROM recipe_table WHERE catalogId = :catalogId")
    suspend fun getRecipesByCatalog(catalogId: Long): List<RecipeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity)

    @Query("UPDATE recipe_table SET isFavorite = :isFavorite WHERE id = :recipeId")
    suspend fun updateFavoriteStatus(recipeId: Long, isFavorite: Boolean)

    @Query("DELETE FROM recipe_table WHERE id = :recipeId")
    suspend fun deleteRecipesById(recipeId: Long)

    @Query("DELETE FROM recipe_table WHERE catalogId = :catalogId")
    suspend fun deleteRecipesByCatalog(catalogId: Long)
}