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
        val imageBytes: ByteArray? =
            if (image is CookLogImage.Bitmap) (image as CookLogImage.Bitmap).data else null
        val resIndex: Int? = if (image is CookLogImage.Resource) {
            val index = RecipeDefaults.list.indexOf((image as CookLogImage.Resource).res)
            if (index != -1) index else null
        } else {
            null
        }
        return RecipeEntity(
            id = id,
            name = name,
            explanation = explanation,
            image = imageBytes,
            resourceIndex = resIndex,
            isFavorite = isFavorite,
            catalogId = catalogId
        )
    }
}