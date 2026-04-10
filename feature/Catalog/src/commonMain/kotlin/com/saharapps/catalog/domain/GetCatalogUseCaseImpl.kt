package com.saharapps.catalog.domain

import com.saharapps.catalog.CatalogItem
import com.saharapps.catalog.data.CatalogRepository
import com.saharapps.common.model.CookLogImage
import com.saharapps.database.catalog.CatalogEntity
import cooklog.feature.catalog.generated.resources.Res
import cooklog.feature.catalog.generated.resources.default

internal class GetCatalogUseCaseImpl(
    private val catalogRepository: CatalogRepository
) : GetCatalogUseCase {
    override suspend fun invoke(): Result<List<CatalogItem>> {
        return catalogRepository.getCatalogs().fold(
            onSuccess = { response ->
                val catalogItems = response.toCatalogItems()
                Result.success(catalogItems)
            },
            onFailure = { exception ->
                Result.failure(exception)
            }
        )
    }

    private fun List<CatalogEntity>.toCatalogItems(): List<CatalogItem> {
        val catalogItems = this.map { entity ->
            val image = if (entity.isResource) {
                CookLogImage.Resource(Res.drawable.default)
            } else {
                CookLogImage.Bitmap(entity.imageData ?: byteArrayOf())
            }
            CatalogItem(
                id = entity.id,
                name = entity.name,
                image = image
            )
        }
        return catalogItems
    }
}