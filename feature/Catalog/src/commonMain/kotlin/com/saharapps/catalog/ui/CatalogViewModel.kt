package com.saharapps.catalog.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saharapps.catalog.domain.GetCatalogUseCase
import com.saharapps.catalog.domain.SaveCatalogUseCase
import com.saharapps.ui.ViewStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CatalogViewModel(
    private val getCatalogUseCase: GetCatalogUseCase,
    private val saveCatalogUseCase: SaveCatalogUseCase
) : ViewModel() {
    private val _catalogUiState = MutableStateFlow(CatalogUiState())
    val catalogUiState = _catalogUiState.asStateFlow()

    fun getCatalogs() {
        viewModelScope.launch {
            _catalogUiState.update {
                it.copy(viewStatus = ViewStatus.SUCCESS)
            }
            val result = getCatalogUseCase.invoke()
            result.onSuccess { catalogs ->
                _catalogUiState.update {
                    it.copy(
                        catalogs = catalogs,
                        viewStatus = ViewStatus.SUCCESS
                    )
                }
            }
            result.onFailure { exception ->
                _catalogUiState.update {
                    it.copy(
                        viewStatus = ViewStatus.FAILED,
                        failedMessage = exception.message
                    )
                }
            }
        }
    }



}