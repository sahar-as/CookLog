package com.saharapps.database.catalog

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CatalogDao {
    @Query("SELECT * FROM catalogs")
    suspend fun getAllCatalogs(): List<CatalogEntity>

    @Insert
    suspend fun insertCatalog(catalog: CatalogEntity)
}