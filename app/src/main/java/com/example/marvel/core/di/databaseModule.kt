package com.example.marvel.core.di

import androidx.room.Room
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
}