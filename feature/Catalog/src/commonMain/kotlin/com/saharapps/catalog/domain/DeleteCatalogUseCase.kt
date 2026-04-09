package com.saharapps.catalog.domain

interface DeleteCatalogUseCase {
    suspend operator fun invoke(id: Long)
}