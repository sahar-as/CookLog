package com.saharapps.cooklog

import org.koin.core.context.startKoin

fun initKoinIos() {
    startKoin {
        modules()
    }
}