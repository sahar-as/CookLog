package com.saharapps.database.catalog

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "catalogs")
data class CatalogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val imageData: ByteArray?,
)
