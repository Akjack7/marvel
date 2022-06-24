package com.example.marvel.core

import android.app.Application
import com.example.marvel.core.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                listOf(
                    dispatcherFactoryModule,
                    viewModelModule,
                    repositoryModule,
                    marvelApiModule,
                    netModule,
                    useCasesModule,
                    dataSourceModule
                )
            )
        }
    }
}