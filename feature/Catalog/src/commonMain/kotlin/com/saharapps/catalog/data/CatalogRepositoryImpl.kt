package com.saharapps.catalog.data

import com.saharapps.database.catalog.CatalogDao
import com.saharapps.database.catalog.CatalogEntity

internal class CatalogRepositoryImpl(
    private val dao: CatalogDao
): CatalogRepository {
    override suspend fun getCatalogs(): Result<List<CatalogEntity>> {
        return Result.success(dao.getAllCatalogs())
    }

    override suspend fun saveCatalog(catalog: CatalogEntity) {
        dao.insertCatalog(catalog)
    }
}