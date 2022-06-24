package com.example.marvel.core.di

import android.app.Application
import com.example.marvel.data.remote.IMarvelRepository
import com.example.marvel.utils.MARVEL_HASH
import com.example.marvel.utils.MARVEL_PUBLIC_KEY
import com.example.marvel.data.remote.apis.MarvelApi
import com.example.marvel.data.remote.MarvelRepository
import com.example.marvel.utils.AppDispatcherFactory
import com.example.marvel.utils.DispatcherFactory
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val dispatcherFactoryModule = module {
    single<DispatcherFactory> {
        AppDispatcherFactory()
    }
}