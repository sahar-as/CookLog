package com.saharapps.recipe_list.ui

import com.saharapps.recipe_list.domain.model.RecipeItem
import com.saharapps.ui.ViewStatus

data class RecipeListUiState(
    val recipes: List<RecipeItem> = emptyList(),
    val viewStatus: ViewStatus = ViewStatus.INITIAL,
    val failedMessage: String? = null
)
