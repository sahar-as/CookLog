package com.saharapps.recipe.domain

import com.saharapps.common.model.CookLogImage
import com.saharapps.common.model.RecipeDefaults
import com.saharapps.common.model.RecipeItem
import com.saharapps.database.recipe.RecipeEntity
import com.saharapps.recipe.data.RecipeRepository
import cooklog.feature.recipe.generated.resources.Res
import cooklog.feature.recipe.generated.resources.default

class GetRecipeByIdUseCaseImpl(
    private val recipeRepository: RecipeRepository
) : GetRecipeByIdUseCase {
    override suspend fun invoke(id: Long): Result<RecipeItem> {
        return recipeRepository.getRecipeById(id).fold(
            onSuccess = { recipeEntities ->
                val recipeItems = recipeEntities.toRecipeItem()
                Result.success(recipeItems)
            },
            onFailure = { exception ->
                Result.failure(exception)
            }
        )
    }

    private fun RecipeEntity.toRecipeItem(): RecipeItem {
        val image = getImages(this)
        val recipeItem =
            RecipeItem(
                id = id,
                name = name,
                explanation = explanation,
                images = image,
                cookTime = cookTime,
                isFavorite = isFavorite,
                catalogId = catalogId
            )

        return recipeItem
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