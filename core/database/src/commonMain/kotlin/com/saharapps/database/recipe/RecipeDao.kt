package com.saharapps.database.recipe

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saharapps.database.catalog.CatalogEntity

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe_table WHERE id = :recipeId")
    suspend fun getRecipe(recipeId: Long)

    @Query("SELECT * FROM recipe_table WHERE catalogId = :catalogId")
    suspend fun getAllRecipeOfCatalog(catalogId: Long): List<CatalogEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity)

    @Query("UPDATE recipe_table SET isFavorite = :isFavorite WHERE id = :recipeId")
    suspend fun updateFavoriteStatus(recipeId: Long, isFavorite: Boolean)

    @Query("DELETE FROM recipe_table WHERE id = :recipeId")
    suspend fun deleteRecipesById(recipeId: Long)

    @Query("DELETE FROM recipe_table WHERE catalogId = :catalogId")
    suspend fun deleteRecipesByCatalog(catalogId: Long)
}