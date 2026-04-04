package com.saharapps.catalog.di

import com.saharapps.catalog.data.CatalogRepository
import com.saharapps.catalog.data.CatalogRepositoryImpl
import com.saharapps.catalog.ui.CatalogViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val catalogModule = module {
    single<CatalogRepository> { CatalogRepositoryImpl(get()) }
    viewModel { CatalogViewModel() }
}