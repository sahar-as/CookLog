package com.saharapps.recipe_list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saharapps.recipe_list.domain.DeleteRecipeUseCase
import com.saharapps.recipe_list.domain.GetRecipesByCatalogUseCase
import com.saharapps.recipe_list.domain.UpdateFavoriteStatusUseCase
import com.saharapps.ui.ViewStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeListViewModel(
    private val getRecipesByCatalogUseCase: GetRecipesByCatalogUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val updateFavoriteStatusUseCase: UpdateFavoriteStatusUseCase
) : ViewModel() {
    private val _recipeListUiState = MutableStateFlow(RecipeListUiState())
    val recipeListUiState = _recipeListUiState.asStateFlow()

    fun getRecipesByCatalog(catalogId: Long) {
        viewModelScope.launch {
            _recipeListUiState.update {
                it.copy(viewStatus = ViewStatus.LOADING)
            }
            val result = getRecipesByCatalogUseCase(catalogId)
            result.onSuccess { recipeItems ->
                _recipeListUiState.update {
                    it.copy(
                        viewStatus = ViewStatus.SUCCESS,
                        recipes = recipeItems
                    )
                }
            }
            result.onFailure { exception ->
                _recipeListUiState.update {
                    it.copy(
                        viewStatus = ViewStatus.FAILED,
                        failedMessage = exception.message
                    )
                }
            }
        }
    }

    fun deleteRecipe(recipeId: Long){
        viewModelScope.launch {
            deleteRecipeUseCase(recipeId)
        }
    }

    fun updateFavoriteState(
        recipeId: Long,
        isFavorite: Boolean
    ){
        viewModelScope.launch {
            _recipeListUiState.update { currentState ->
                val updatedList = currentState.recipes.map { recipe ->
                    if (recipe.id == recipeId) {
                        recipe.copy(isFavorite = isFavorite)
                    } else {
                        recipe
                    }
                }
                currentState.copy(recipes = updatedList)
            }

            updateFavoriteStatusUseCase(
                recipeId = recipeId,
                isFavorite = isFavorite
            )
        }
    }
}