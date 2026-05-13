package com.saharapps.catalog.usecase

import com.saharapps.catalog.CatalogItem
import com.saharapps.catalog.domain.GetCatalogUseCase

class FakeGetCatalogUseCase : GetCatalogUseCase {
    var result: Result<List<CatalogItem>> = Result.success(emptyList())
    override suspend fun invoke(): Result<List<CatalogItem>> = result
}