package com.saharapps.catalog.domain

import com.saharapps.catalog.CatalogItem

interface SaveCatalogUseCase {
    suspend operator fun invoke(catalogItem: CatalogItem)
}