package com.saharapps.database.catalog

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CatalogDao {
    @Query("SELECT * FROM catalogs")
    fun getAllCatalogs(): Flow<List<CatalogEntity>>

    @Insert
    suspend fun insert(catalog: CatalogEntity)
}