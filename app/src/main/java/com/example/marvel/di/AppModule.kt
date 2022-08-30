package com.example.marvel.di

import com.example.marvel.utils.AppDispatcherFactory
import com.example.marvel.utils.DispatcherFactory
import com.task.data.error.mapper.ErrorMapper
import com.task.usecase.errors.ErrorManager
import org.koin.dsl.module

val appModule = module {
    single<DispatcherFactory> {
        AppDispatcherFactory()
    }

    single { ErrorMapper(get()) }
    single { ErrorManager(get()) }
}

