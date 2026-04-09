package com.saharapps.catalog.di

import com.saharapps.catalog.data.CatalogRepository
import com.saharapps.catalog.data.CatalogRepositoryImpl
import com.saharapps.catalog.domain.DeleteCatalogUseCase
import com.saharapps.catalog.domain.DeleteCatalogUseCaseImpl
import com.saharapps.catalog.domain.GetCatalogUseCase
import com.saharapps.catalog.domain.GetCatalogUseCaseImpl
import com.saharapps.catalog.domain.SaveCatalogUseCase
import com.saharapps.catalog.domain.SaveCatalogUseCaseImpl
import com.saharapps.catalog.ui.CatalogViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val catalogModule = module {
    factory<CatalogRepository> { CatalogRepositoryImpl(get()) }
    factory<GetCatalogUseCase> { GetCatalogUseCaseImpl(get()) }
    factory<SaveCatalogUseCase> { SaveCatalogUseCaseImpl(get()) }
    factory<DeleteCatalogUseCase> { DeleteCatalogUseCaseImpl(get()) }
    viewModel {
        CatalogViewModel(
            get(),
            get(),
            get()
        )
    }
}