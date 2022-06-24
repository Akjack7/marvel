package com.example.marvel.core.di

import com.example.marvel.core.di.usecases.UseCasesNamedConstants.GET_CHARACTERS
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
}