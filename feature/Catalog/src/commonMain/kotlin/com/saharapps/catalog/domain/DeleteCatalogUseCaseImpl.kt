package com.saharapps.catalog.domain

import com.saharapps.catalog.data.CatalogRepository

class DeleteCatalogUseCaseImpl(
    private val catalogRepository: CatalogRepository
): DeleteCatalogUseCase {
    override suspend fun invoke(id: Long) {
        catalogRepository.deleteCatalog(id)
    }
}