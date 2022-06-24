package com.example.marvel.core.di

import com.example.marvel.utils.AppDispatcherFactory
import com.example.marvel.utils.DispatcherFactory
import org.koin.dsl.module

val dispatcherFactoryModule = module {
    single<DispatcherFactory> {
        AppDispatcherFactory()
    }
}