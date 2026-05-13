package com.saharapps.catalog.usecase

import com.saharapps.catalog.domain.DeleteCatalogUseCase

class FakeDeleteCatalogUseCase : DeleteCatalogUseCase {
    var lastDeletedId: Long? = null
    override suspend fun invoke(id: Long) {
        lastDeletedId = id
    }
}