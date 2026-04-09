package com.saharapps.cooklog

import com.saharapps.database.di.iosDatabaseModule
import org.koin.core.context.startKoin

fun initKoinIos() {
    startKoin {
        modules(appModule, iosDatabaseModule)
    }
}