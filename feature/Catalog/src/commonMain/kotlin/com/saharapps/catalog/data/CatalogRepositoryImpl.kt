package com.saharapps.catalog.data

import com.saharapps.database.catalog.CatalogDao
import com.saharapps.database.catalog.CatalogEntity

class CatalogRepositoryImpl(
    private val dao: CatalogDao
): CatalogRepository {
    override suspend fun getCatalogs(): Result<List<CatalogEntity>> {
        return dao.getAllCatalogs()
    }

    override suspend fun saveCatalog(catalog: CatalogEntity) {
        dao.insertCatalog(catalog)
    }
}