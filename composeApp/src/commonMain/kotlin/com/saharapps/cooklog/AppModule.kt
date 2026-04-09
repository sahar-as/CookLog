package com.saharapps.cooklog

import com.saharapps.catalog.di.catalogModule
import com.saharapps.database.di.databaseModule
import org.koin.dsl.module

val appModule = module {
    includes(catalogModule, databaseModule)
}