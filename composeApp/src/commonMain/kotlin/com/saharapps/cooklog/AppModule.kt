package com.saharapps.cooklog

import com.saharapps.catalog.di.catalogModule
import org.koin.dsl.module

val appModule = module {
    includes(catalogModule)
}