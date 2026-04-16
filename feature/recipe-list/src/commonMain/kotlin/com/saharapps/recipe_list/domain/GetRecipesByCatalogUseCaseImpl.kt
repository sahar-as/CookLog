package com.saharapps.recipe_list.domain

import com.saharapps.common.model.CookLogImage
import com.saharapps.common.model.RecipeItem
import com.saharapps.database.recipe.RecipeEntity
import com.saharapps.recipe_list.data.RecipeListRepository
import com.saharapps.common.model.RecipeDefaults
import cooklog.feature.recipe_list.generated.resources.Res
import cooklog.feature.recipe_list.generated.resources.default

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
            val images = getImages(entity)
            RecipeItem(
                id = entity.id,
                name = entity.name,
                explanation = entity.explanation,
                images = images,
                isFavorite = entity.isFavorite,
                catalogId = entity.catalogId
            )
        }
        return recipeItems
    }

    private fun getImages(entity: RecipeEntity): List<CookLogImage> {
        val list = mutableListOf<CookLogImage>()

        entity.images?.let {
            list.add(CookLogImage.Bitmap(it))
        }

        entity.resourceIndices?.split(",")
            ?.filter { it.isNotBlank() }
            ?.forEach { indexStr ->
                val index = indexStr.toIntOrNull()
                if (index != null && index in RecipeDefaults.list.indices) {
                    list.add(CookLogImage.Resource(RecipeDefaults.list[index]))
                }
            }

        if (list.isEmpty()) {
            list.add(CookLogImage.Resource(Res.drawable.default))
        }

        return list
    }
}