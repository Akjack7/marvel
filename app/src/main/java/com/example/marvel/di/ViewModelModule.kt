package com.example.marvel.di

import com.example.marvel.di.usecases.UseCasesNamedConstants.CHANGE_FAVORITE
import com.example.marvel.di.usecases.UseCasesNamedConstants.GET_CHARACTER
import com.example.marvel.di.usecases.UseCasesNamedConstants.GET_CHARACTERS
import com.example.marvel.ui.detail.CharacterDetailViewModel
import com.example.marvel.ui.general.GeneralCharactersViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        GeneralCharactersViewModel(
            get(),
            getCharactersUseCase = get(
                named(GET_CHARACTERS)
            ),
            get()
        )
    }

    viewModel {
        CharacterDetailViewModel(
            get(),
            getCharactersDetailUseCase = get(
                named(GET_CHARACTER)
            ),
            changeFavoriteCharacterUseCase = get(named(CHANGE_FAVORITE)),
            get()
        )
    }
}