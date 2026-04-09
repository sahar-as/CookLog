package com.saharapps.database.di

import com.saharapps.database.DatabaseFactory
import org.koin.dsl.module

val iosDatabaseModule = module {
    single { DatabaseFactory() }
}