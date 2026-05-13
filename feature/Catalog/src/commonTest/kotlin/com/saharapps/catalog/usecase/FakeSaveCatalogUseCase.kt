package com.saharapps.catalog.usecase

import com.saharapps.catalog.CatalogItem
import com.saharapps.catalog.domain.SaveCatalogUseCase

class FakeSaveCatalogUseCase : SaveCatalogUseCase {
    var lastSavedItem: CatalogItem? = null
    override suspend fun invoke(item: CatalogItem) {
        lastSavedItem = item
    }
}