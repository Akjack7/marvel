package com.example.marvel.core.di

import com.example.marvel.core.di.usecases.UseCasesNamedConstants.GET_CHARACTERS
import com.example.marvel.data.IMarvelRepository
import com.example.marvel.domain.usecases.GetCharactersBaseUseCase
import com.example.marvel.domain.usecases.GetCharactersUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCasesModule = module {
    single(named(GET_CHARACTERS)) { provideGetCakes(get()) }
}

fun provideGetCakes(repository: IMarvelRepository): GetCharactersBaseUseCase {
    return GetCharactersUseCase(repository)
}