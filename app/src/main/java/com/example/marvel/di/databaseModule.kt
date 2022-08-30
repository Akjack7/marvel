package com.example.marvel.di

import androidx.room.Room
import com.example.marvel.data.local.ILocalRepository
import com.example.marvel.data.local.LocalRepository
import com.example.marvel.data.local.database.CharactersDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

const val DATABASE_NAME = "characters_database"

val dataBaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            CharactersDatabase::class.java,
            DATABASE_NAME
        )
            .build()
    }

    single<ILocalRepository> { LocalRepository(get()) }
    factory {
        get<CharactersDatabase>().characterDao()
    }

    single {
        LocalRepository(get())
    }
}