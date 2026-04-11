package com.saharapps.recipe.ui

import com.saharapps.common.model.RecipeItem
import com.saharapps.ui.ViewStatus

data class RecipeUiState (
    val recipe: RecipeItem? = null,
    val viewStatus: ViewStatus = ViewStatus.INITIAL,
    val failedMessage: String? = null
)