package com.saharapps.recipe_list.domain

import com.saharapps.common.model.RecipeItem
import com.saharapps.database.recipe.RecipeEntity
import com.saharapps.recipe_list.data.RecipeListRepository

internal class GetRecipesByCatalogUseCaseImpl(
    private val recipeListRepository: RecipeListRepository
) : GetRecipesByCatalogUseCase {
    override suspend fun invoke(catalogId: Long): Result<List<RecipeItem>> {
        return recipeListRepository.getRecipesByCatalog(catalogId).fold(
            onSuccess = { recipeEntities ->
                val recipeItems = recipeEntities.toRecipeItems()
                Result.success(recipeItems)
            },
            onFailure = { exception ->
                Result.failure(exception)
            }
        )
    }

    private fun List<RecipeEntity>.toRecipeItems(): List<RecipeItem> {
        val recipeItems = this.map { entity ->
            RecipeItem(
                id = entity.id,
                name = entity.name,
                explanation = entity.explanation,
                images = entity.images,
                isFavorite = entity.isFavorite,
                catalogId = entity.catalogId
            )
        }
        return recipeItems
    }
}