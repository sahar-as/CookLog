package com.saharapps.database.di

import com.saharapps.database.CookLogDatabase
import com.saharapps.database.DatabaseFactory
import org.koin.dsl.module

val databaseModule = module {

    single<CookLogDatabase> {
        get<DatabaseFactory>().create()
    }

    single {
        get<CookLogDatabase>().catalogDao()
    }
}