package com.saharapps.catalog.ui

import com.saharapps.catalog.CatalogItem
import com.saharapps.ui.ViewStatus

data class CatalogUiState(
    val catalogs: List<CatalogItem> = emptyList(),
    val viewStatus: ViewStatus = ViewStatus.INITIAL
)