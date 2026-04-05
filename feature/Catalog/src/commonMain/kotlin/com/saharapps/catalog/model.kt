package com.saharapps.catalog

import org.jetbrains.compose.resources.DrawableResource

sealed class CatalogImage {
    data class Resource(val res: DrawableResource) : CatalogImage()
    data class Bitmap(val data: ByteArray) : CatalogImage()
}

data class CatalogItem(
    val id: Long = 0,
    val name: String,
    val image: CatalogImage
)