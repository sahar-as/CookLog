package com.saharapps.recipe.domain

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
        return RecipeEntity(
            id = id,
            name = name,
            explanation = explanation,
            images = images,
            cookTime = cookTime,
            isFavorite = isFavorite,
            catalogId = catalogId
        )
    }
}