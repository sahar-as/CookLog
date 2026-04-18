package com.saharapps.catalog

data class CatalogItem(
    val id: Long = 0,
    val name: String,
    val image: ByteArray?
)