package com.example.marvel.presentation.general

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.marvel.domain.models.Character
import com.example.marvel.domain.usecases.GetCharactersBaseUseCase
import com.example.marvel.presentation.base.BaseViewModel
import com.example.marvel.utils.DispatcherFactory
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GeneralCharactersViewModel(
    private val dispatcherFactory: DispatcherFactory,
    private val getCharactersUseCase: GetCharactersBaseUseCase
) : BaseViewModel(dispatcherFactory) {

    private val _allCharactersState = MutableLiveData<CharactersState>()
    val allCharactersState: LiveData<CharactersState>
        get() = _allCharactersState


    fun getCharacters() {
        launch {
            withContext(dispatcherFactory.getIO()) {
                getCharactersUseCase(Unit).onStart {
                    _allCharactersState.postValue(CharactersState.Loading)
                }
                    .catch { _allCharactersState.postValue(CharactersState.Error) }.collect {
                        if (it.isNotEmpty()) {
                            _allCharactersState.postValue(CharactersState.Loaded(it))
                        } else {
                            _allCharactersState.postValue(CharactersState.Empty)
                        }
                    }
            }
        }
    }
}


sealed class CharactersState {
    object Loading : CharactersState()
    class Loaded(val data: List<Character>) : CharactersState()
    object Error : CharactersState()
    object Empty : CharactersState()
}