package com.example.marvel.presentation.general

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.marvel.data.Resource
import com.example.marvel.domain.models.Character
import com.example.marvel.domain.usecases.GetCharactersBaseUseCase
import com.example.marvel.presentation.base.BaseViewModel
import com.example.marvel.utils.DispatcherFactory
import com.example.marvel.utils.SingleEvent
import com.task.data.error.DEFAULT_ERROR
import com.task.usecase.errors.ErrorManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GeneralCharactersViewModel(
    private val dispatcherFactory: DispatcherFactory,
    private val getCharactersUseCase: GetCharactersBaseUseCase,
    private val errorManager: ErrorManager
) : BaseViewModel(dispatcherFactory) {

    private val _allCharactersState = MutableLiveData<Resource<List<Character>>>()
    val allCharactersState: LiveData<Resource<List<Character>>>
        get() = _allCharactersState

    private val showToastPrivate = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = showToastPrivate

    init {
        getCharacters()
    }


    fun getCharacters() {
        launch {
            withContext(dispatcherFactory.getIO()) {
                getCharactersUseCase(Unit).onStart {
                    _allCharactersState.postValue(Resource.Loading())
                }
                    .catch { _allCharactersState.postValue(Resource.DataError(errorCode = DEFAULT_ERROR)) }
                    .collect {
                        _allCharactersState.postValue(it)

                    }
            }
        }
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.value = SingleEvent(error.description)
    }
}