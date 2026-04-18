package com.saharapps.recipe.domain

import com.saharapps.common.model.RecipeItem
import com.saharapps.database.recipe.RecipeEntity
import com.saharapps.recipe.data.RecipeRepository

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
        val recipeItem =
            RecipeItem(
                id = id,
                name = name,
                explanation = explanation,
                images = images,
                cookTime = cookTime,
                isFavorite = isFavorite,
                catalogId = catalogId
            )

        return recipeItem
    }
}