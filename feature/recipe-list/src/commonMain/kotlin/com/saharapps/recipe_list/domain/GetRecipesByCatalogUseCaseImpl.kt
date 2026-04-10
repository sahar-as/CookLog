package com.saharapps.recipe_list.domain

import com.saharapps.common.model.CookLogImage
import com.saharapps.common.model.RecipeItem
import com.saharapps.database.recipe.RecipeEntity
import com.saharapps.recipe_list.data.RecipeListRepository
import com.saharapps.common.model.RecipeDefaults
import cooklog.feature.recipe_list.generated.resources.Res
import cooklog.feature.recipe_list.generated.resources.default
import kotlin.ranges.contains

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
            val image = getImage(entity)
            RecipeItem(
                id = entity.id,
                name = entity.name,
                explanation = entity.explanation,
                image = image,
                isFavorite = entity.isFavorite,
                catalogId = entity.catalogId
            )
        }
        return recipeItems
    }

    private fun getImage(entity: RecipeEntity): CookLogImage {
        return when {
            entity.image != null -> {
                CookLogImage.Bitmap(entity.image!!)
            }

            entity.resourceIndex != null && entity.resourceIndex in RecipeDefaults.list.indices -> {
                CookLogImage.Resource(RecipeDefaults.list[entity.resourceIndex!!])
            }

            else -> {
                CookLogImage.Resource(Res.drawable.default)
            }
        }
    }
}