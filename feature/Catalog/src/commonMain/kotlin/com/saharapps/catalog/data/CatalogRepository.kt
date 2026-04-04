package com.saharapps.catalog.data

import com.saharapps.database.catalog.CatalogEntity

interface CatalogRepository {
    suspend fun getCatalogs(): Result<List<CatalogEntity>>
    suspend fun saveCatalog(catalog: CatalogEntity)
}