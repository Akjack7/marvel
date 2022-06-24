package com.example.marvel.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.marvel.domain.models.Character
import com.example.marvel.domain.usecases.ChangeFavoriteCharacterBaseUseCase
import com.example.marvel.domain.usecases.GetDetailCharacterBaseUseCase
import com.example.marvel.presentation.BaseViewModel
import com.example.marvel.utils.DispatcherFactory
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterDetailViewModel(
    private val dispatcherFactory: DispatcherFactory,
    private val getCharactersDetailUseCase: GetDetailCharacterBaseUseCase,
    private val changeFavoriteCharacterUseCase: ChangeFavoriteCharacterBaseUseCase
) : BaseViewModel(dispatcherFactory) {

    private val _characterState = MutableLiveData<CharacterState>()
    val characterState: LiveData<CharacterState>
        get() = _characterState


    fun getCharacter(id: Int) {
        launch {
            withContext(dispatcherFactory.getIO()) {
                getCharactersDetailUseCase(id).onStart {
                    _characterState.postValue(CharacterState.Loading)
                }
                    .catch { _characterState.postValue(CharacterState.Error) }.collect {
                        _characterState.postValue(CharacterState.Loaded(it))
                    }
            }
        }
    }

    fun changeFavoriteCharacter(character: Character) {
        launch {
            withContext(dispatcherFactory.getIO()) {
                changeFavoriteCharacterUseCase(character).collect()
            }
        }
    }
}

sealed class CharacterState {
    object Loading : CharacterState()
    class Loaded(val data: Character) : CharacterState()
    object Error : CharacterState()
}