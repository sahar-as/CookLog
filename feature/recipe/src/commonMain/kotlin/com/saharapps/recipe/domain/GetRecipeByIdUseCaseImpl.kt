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
        val image = getImage(this)
        val recipeItem =
            RecipeItem(
                id = id,
                name = name,
                explanation = explanation,
                image = image,
                isFavorite = isFavorite,
                catalogId = catalogId
            )

        return recipeItem
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