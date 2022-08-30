package com.example.marvel.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.marvel.data.Resource
import com.example.marvel.domain.models.Character
import com.example.marvel.domain.usecases.ChangeFavoriteCharacterBaseUseCase
import com.example.marvel.domain.usecases.GetDetailCharacterBaseUseCase
import com.example.marvel.presentation.base.BaseViewModel
import com.example.marvel.utils.DispatcherFactory
import com.example.marvel.utils.SingleEvent
import com.task.data.error.DEFAULT_ERROR
import com.task.usecase.errors.ErrorManager
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterDetailViewModel(
    private val dispatcherFactory: DispatcherFactory,
    private val getCharactersDetailUseCase: GetDetailCharacterBaseUseCase,
    private val changeFavoriteCharacterUseCase: ChangeFavoriteCharacterBaseUseCase,
    private val errorManager: ErrorManager
) : BaseViewModel(dispatcherFactory) {

    private val _characterState = MutableLiveData<Resource<Character>>()
    val characterState: LiveData<Resource<Character>>
        get() = _characterState

    private val showToastPrivate = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = showToastPrivate


    fun getCharacter(id: Int) {
        launch {
            withContext(dispatcherFactory.getIO()) {
                getCharactersDetailUseCase(id).onStart {
                    _characterState.postValue(Resource.Loading())
                }
                    .catch {
                        _characterState.postValue(Resource.DataError(DEFAULT_ERROR)) }.collect {
                        _characterState.postValue(it)
                    }
            }
        }
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.value = SingleEvent(error.description)
    }

    fun changeFavoriteCharacter(character: Character) {
        launch {
            withContext(dispatcherFactory.getIO()) {
                changeFavoriteCharacterUseCase(character).collect()
            }
        }
    }
}