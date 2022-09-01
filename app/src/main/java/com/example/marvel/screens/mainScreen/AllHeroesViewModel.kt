package com.example.marvel.screens.mainScreen

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.marvel.data.Resource
import com.example.marvel.domain.models.Character
import com.example.marvel.domain.usecases.GetCharactersBaseUseCase
import com.example.marvel.ui.base.BaseViewModel
import com.example.marvel.utils.DispatcherFactory
import com.example.marvel.utils.SingleEvent
import com.task.data.error.DEFAULT_ERROR
import com.task.usecase.errors.ErrorManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllHeroesViewModel(
    private val dispatcherFactory: DispatcherFactory,
    private val getCharactersUseCase: GetCharactersBaseUseCase,
    private val errorManager: ErrorManager
) : BaseViewModel(dispatcherFactory) {

    val data : MutableState<Resource<List<Character>>> = mutableStateOf(Resource.Loading())

    init {
        getCharacters()
    }


    private fun getCharacters() {
        launch {
            withContext(dispatcherFactory.getIO()) {
                getCharactersUseCase(Unit).onStart {
                    data.value = Resource.Loading()
                }
                    .catch { data.value = Resource.DataError(errorCode = DEFAULT_ERROR) }
                    .collect {
                        data.value = it
                    }
            }
        }
    }

    fun showToastMessage(context:Context) {
        val error = errorManager.getError(data.value.errorCode?: DEFAULT_ERROR)
        Toast.makeText(context, error.description, Toast.LENGTH_SHORT).show()
    }
}