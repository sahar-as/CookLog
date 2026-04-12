package com.saharapps.recipe.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saharapps.recipe.domain.GetRecipeByIdUseCase
import com.saharapps.recipe.domain.UpdateFavoriteStatusUseCase
import com.saharapps.ui.ViewStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase,
    private val updateFavoriteStatusUseCase: UpdateFavoriteStatusUseCase,
    ): ViewModel() {
    private val _recipeUiState = MutableStateFlow(RecipeUiState())
    val recipeUiState = _recipeUiState.asStateFlow()
    fun getRecipeById(id: Long) {
        viewModelScope.launch {
            if (_recipeUiState.value.recipe == null) {
                _recipeUiState.update { it.copy(viewStatus = ViewStatus.LOADING) }
            }
            val result = getRecipeByIdUseCase(id)
            result.onSuccess { recipe ->
                _recipeUiState.update {
                    it.copy(
                        recipe = recipe,
                        viewStatus = ViewStatus.SUCCESS
                    )
                }
            }
            result.onFailure { exception ->
                _recipeUiState.update {
                    it.copy(
                        viewStatus = ViewStatus.FAILED,
                        failedMessage = exception.message
                    )
                }
            }
        }
    }
    fun updateFavoriteState(
        recipeId: Long,
        isFavorite: Boolean
    ){
        viewModelScope.launch {
            updateFavoriteStatusUseCase(
                recipeId = recipeId,
                isFavorite = isFavorite
            )
            getRecipeById(recipeId)
        }
    }
}