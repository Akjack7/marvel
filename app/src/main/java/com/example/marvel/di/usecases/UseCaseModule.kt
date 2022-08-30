package com.example.marvel.di

import com.example.marvel.di.usecases.UseCasesNamedConstants.CHANGE_FAVORITE
import com.example.marvel.di.usecases.UseCasesNamedConstants.GET_CHARACTER
import com.example.marvel.di.usecases.UseCasesNamedConstants.GET_CHARACTERS
import com.example.marvel.data.local.ILocalRepository
import com.example.marvel.data.remote.IMarvelRepository
import com.example.marvel.domain.usecases.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCasesModule = module {
    single(named(GET_CHARACTERS)) { provideGetCharacters(get()) }
    single(named(GET_CHARACTER)) { provideGetDetailCharacter(get(), get()) }
    single(named(CHANGE_FAVORITE)) { provideChangeFavoriteCharacter(get()) }
}

fun provideGetCharacters(repository: IMarvelRepository): GetCharactersBaseUseCase {
    return GetCharactersUseCase(repository)
}

fun provideGetDetailCharacter(
    repository: IMarvelRepository,
    localRepository: ILocalRepository
): GetDetailCharacterBaseUseCase {
    return GetDetailCharacterUseCase(repository, localRepository)
}

fun provideChangeFavoriteCharacter(repository: ILocalRepository): ChangeFavoriteCharacterBaseUseCase {
    return ChangeFavoriteCharacterUseCase(repository)
}