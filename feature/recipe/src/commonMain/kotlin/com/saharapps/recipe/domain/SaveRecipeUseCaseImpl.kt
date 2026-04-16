package com.saharapps.recipe.domain

import com.saharapps.common.model.CookLogImage
import com.saharapps.common.model.RecipeDefaults
import com.saharapps.common.model.RecipeItem
import com.saharapps.database.recipe.RecipeEntity
import com.saharapps.recipe.data.RecipeRepository

class SaveRecipeUseCaseImpl(
    private val recipeRepository: RecipeRepository
) : SaveRecipeUseCase {
    override suspend fun invoke(recipe: RecipeItem) {
        val recipeEntity = recipe.toRecipeEntity()
        recipeRepository.saveRecipe(recipeEntity)
    }

    private fun RecipeItem.toRecipeEntity(): RecipeEntity {
        val imageBytes: ByteArray? = images
            .filterIsInstance<CookLogImage.Bitmap>()
            .firstOrNull()?.data
        val resIndicesString: String = images
            .filterIsInstance<CookLogImage.Resource>()
            .map { resource ->
                RecipeDefaults.list.indexOf(resource.res)
            }
            .filter { it != -1 }
            .joinToString(separator = ",")

        return RecipeEntity(
            id = id,
            name = name,
            explanation = explanation,
            images = imageBytes,
            cookTime = cookTime,
            resourceIndices = resIndicesString,
            isFavorite = isFavorite,
            catalogId = catalogId
        )
    }
}