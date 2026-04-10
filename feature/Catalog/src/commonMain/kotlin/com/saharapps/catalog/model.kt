package com.saharapps.catalog

import com.saharapps.common.model.CookLogImage

data class CatalogItem(
    val id: Long = 0,
    val name: String,
    val image: CookLogImage
)