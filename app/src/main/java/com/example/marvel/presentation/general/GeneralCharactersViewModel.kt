package com.example.marvel.presentation.general

import Results
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.marvel.domain.MarvelRepository
import com.example.marvel.presentation.BaseViewModel
import com.example.marvel.utils.DispatcherFactory
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GeneralCharactersViewModel(
    private val repository: MarvelRepository,
    private val dispatcherFactory: DispatcherFactory
) : BaseViewModel(dispatcherFactory) {

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    val data = MutableLiveData<List<Results>>()

    fun getCharacters() {
        launch {
            withContext(dispatcherFactory.getIO()) {
                try {
                    _loadingState.postValue(LoadingState.LOADING)
                    val results =
                        repository.getCharacters()
                    data.value = results.data.results
                    _loadingState.postValue(LoadingState.LOADED)
                } catch (e: Exception) {
                    _loadingState.postValue(LoadingState.error(e.message))
                }
            }
        }
    }
}