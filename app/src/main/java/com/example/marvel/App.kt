package com.example.marvel

import android.app.Application
import com.example.marvel.di.*
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
                    appModule,
                    dataModule,
                    netModule,
                    dataBaseModule,
                    useCasesModule,
                    viewModelModule,
                )
            )
        }
    }
}