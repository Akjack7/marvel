package com.example.marvel.di

import com.example.marvel.di.usecases.UseCasesNamedConstants.CHANGE_FAVORITE
import com.example.marvel.di.usecases.UseCasesNamedConstants.GET_CHARACTER
import com.example.marvel.di.usecases.UseCasesNamedConstants.GET_CHARACTERS
import com.example.marvel.screens.detailSreen.DetailViewModel
import com.example.marvel.screens.mainScreen.AllHeroesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        AllHeroesViewModel(
            get(),
            getCharactersUseCase = get(
                named(GET_CHARACTERS)
            ),
            get()
        )
    }

    viewModel {
        DetailViewModel(
            get(),
            getCharactersDetailUseCase = get(
                named(GET_CHARACTER)
            ),
            changeFavoriteCharacterUseCase = get(named(CHANGE_FAVORITE)),
            get()
        )
    }
}
