package com.saharapps.catalog.di

import com.saharapps.catalog.data.CatalogRepository
import com.saharapps.catalog.data.CatalogRepositoryImpl
import com.saharapps.catalog.domain.GetCatalogUseCase
import com.saharapps.catalog.domain.GetCatalogUseCaseImpl
import com.saharapps.catalog.domain.SaveCatalogUseCase
import com.saharapps.catalog.domain.SaveCatalogUseCaseImpl
import com.saharapps.catalog.ui.CatalogViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val catalogModule = module {
    single<CatalogRepository> { CatalogRepositoryImpl(get()) }
    single<GetCatalogUseCase> { GetCatalogUseCaseImpl(get()) }
    single<SaveCatalogUseCase> { SaveCatalogUseCaseImpl(get()) }
    viewModel { CatalogViewModel() }
}