package com.saharapps.catalog.domain

import com.saharapps.catalog.CatalogItem

interface GetCatalogUseCase {
    suspend operator fun invoke(): Result<List<CatalogItem>>
}