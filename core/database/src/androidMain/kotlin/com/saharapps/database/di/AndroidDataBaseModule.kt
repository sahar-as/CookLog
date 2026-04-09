package com.saharapps.database.di

import com.saharapps.database.DatabaseFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidDatabaseModule = module {
    single {
        DatabaseFactory(androidContext())
    }
}