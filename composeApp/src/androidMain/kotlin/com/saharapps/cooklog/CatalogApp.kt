package com.saharapps.cooklog

import android.app.Application
import android.content.Context
import com.saharapps.database.di.androidDatabaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class CatalogApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoinAndroid(this@CatalogApp)
    }
}

fun initKoinAndroid(context: Context) {
    startKoin {
        androidContext(context)
        androidLogger()
        modules(
            appModule,
            androidDatabaseModule
        )
    }
}