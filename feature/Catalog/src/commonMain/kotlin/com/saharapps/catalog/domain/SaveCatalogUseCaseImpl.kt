package com.saharapps.catalog.domain

import com.saharapps.catalog.CatalogItem
import com.saharapps.catalog.data.CatalogRepository
import com.saharapps.common.model.CookLogImage
import com.saharapps.database.catalog.CatalogEntity

internal class SaveCatalogUseCaseImpl(
    private val catalogRepository: CatalogRepository
) : SaveCatalogUseCase {
    override suspend fun invoke(catalogItem: CatalogItem) {
        val catalogEntity = catalogItem.toCatalogEntity()
        catalogRepository.saveCatalog(catalogEntity)
    }

    private fun CatalogItem.toCatalogEntity(): CatalogEntity {
        val image = this.image
        val imageData = if (image is CookLogImage.Bitmap) image.data else null
        val isResource = image is CookLogImage.Resource

        return CatalogEntity(
            id = this.id,
            name = this.name,
            imageData = imageData,
            isResource = isResource,
        )
    }
}