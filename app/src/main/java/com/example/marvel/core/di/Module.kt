package com.example.marvel.core.di

import android.app.Application
import com.example.marvel.data.IMarvelRepository
import com.example.marvel.utils.MARVEL_HASH
import com.example.marvel.utils.MARVEL_PUBLIC_KEY
import com.example.marvel.data.apis.MarvelApi
import com.example.marvel.data.MarvelRepository
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

val repositoryModule = module {
    single {
        MarvelRepository(get())
    }
}


val marvelApiModule = module {
    fun provideUserApi(retrofit: Retrofit): MarvelApi {
        return retrofit.create(MarvelApi::class.java)
    }

    single { provideUserApi(get()) }
}

val dataSourceModule = module {
    single<IMarvelRepository> { MarvelRepository(get()) }
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
            .addQueryParameter("ts", "1")
            .addQueryParameter("apikey", MARVEL_PUBLIC_KEY)
            .addQueryParameter("hash", MARVEL_HASH)
            .build()
        val request = original.newBuilder().url(url)
        chain.proceed(request.build())
    }

    val apiClient = OkHttpClient().newBuilder().addInterceptor(interceptor).build()


    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder().client(apiClient)
            .baseUrl("https://gateway.marvel.com/")
            .addConverterFactory(GsonConverterFactory.create(factory))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(OkHttpClient().newBuilder().addInterceptor(interceptor).build())
            .build()
    }

    single { provideCache(androidApplication()) }
    single { provideHttpClient(get()) }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }

}


