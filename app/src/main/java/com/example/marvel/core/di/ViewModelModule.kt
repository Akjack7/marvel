package com.example.marvel.core.di

import com.example.marvel.core.di.usecases.UseCasesNamedConstants.CHANGE_FAVORITE
import com.example.marvel.core.di.usecases.UseCasesNamedConstants.GET_CHARACTER
import com.example.marvel.core.di.usecases.UseCasesNamedConstants.GET_CHARACTERS
import com.example.marvel.presentation.detail.CharacterDetailViewModel
import com.example.marvel.presentation.general.GeneralCharactersViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        GeneralCharactersViewModel(
            get(),
            getCharactersUseCase = get(
                named(GET_CHARACTERS)
            )
        )
    }

    viewModel {
        CharacterDetailViewModel(
            get(),
            getCharactersDetailUseCase = get(
                named(GET_CHARACTER)
            ),
            changeFavoriteCharacterUseCase = get(named(CHANGE_FAVORITE))
        )
    }
}