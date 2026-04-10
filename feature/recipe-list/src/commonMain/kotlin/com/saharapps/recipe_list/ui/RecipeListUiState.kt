package com.saharapps.recipe_list.ui

import com.saharapps.common.model.RecipeItem
import com.saharapps.ui.ViewStatus

data class RecipeListUiState(
    val recipes: List<RecipeItem> = emptyList(),
    val viewStatus: ViewStatus = ViewStatus.INITIAL,
    val failedMessage: String? = null
)
