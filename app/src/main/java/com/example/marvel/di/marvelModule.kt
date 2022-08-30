package com.example.marvel.di

import android.app.Application
import android.content.Context
import com.example.marvel.BuildConfig
import com.example.marvel.data.remote.IMarvelRepository
import com.example.marvel.data.remote.MarvelRepository
import com.example.marvel.data.remote.service.MarvelApi
import com.example.marvel.data.remote.service.MarvelDataSource
import com.example.marvel.utils.Network
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.task.data.error.mapper.ErrorMapper
import com.task.usecase.errors.ErrorManager
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://gateway.marvel.com/"
const val HASH = "hash"
const val API_KEY = "apikey"
const val TS = "ts"
const val TS_VALUE = "1"

val dataSourceModule = module {
    fun provideNetwork(context: Context) : Network {
        return Network(context)
    }
    single { provideNetwork(get()) }
    single { MarvelDataSource(get(),get()) }
}

val repositoryModule = module {
    single {
        MarvelRepository(get(),get())
    }
}

val marvelApiModule = module {
    fun provideUserApi(retrofit: Retrofit): MarvelApi {
        return retrofit.create(MarvelApi::class.java)
    }

    single { provideUserApi(get()) }
}

val iMarvelModule = module {
    single<IMarvelRepository> { MarvelRepository(get(),get()) }
}


val netModule = module {
    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideHttpClient(cache: Cache): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .cache(cache)

        return okHttpClientBuilder.build()
    }

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    val interceptor = Interceptor { chain ->
        val original = chain.request()
        val originalHttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(TS, TS_VALUE)
            .addQueryParameter(API_KEY, BuildConfig.MARVEL_PUBLIC_KEY)
            .addQueryParameter(HASH, BuildConfig.MARVEL_HASH)
            .build()
        val request = original.newBuilder().url(url)
        chain.proceed(request.build())
    }

    val apiClient = OkHttpClient().newBuilder().addInterceptor(interceptor).build()


    fun provideRetrofit(factory: Gson): Retrofit {
        return Retrofit.Builder().client(apiClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(OkHttpClient().newBuilder().addInterceptor(interceptor).build())
            .build()
    }

    single { provideCache(androidApplication()) }
    single { provideHttpClient(get()) }
    single { provideGson() }
    single { provideRetrofit(get()) }
}

val errorModule = module {
    single { ErrorMapper(get()) }
    single { ErrorManager(get()) }
}