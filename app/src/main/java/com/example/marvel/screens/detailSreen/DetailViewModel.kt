package com.example.marvel.screens.detailSreen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.marvel.data.Resource
import com.example.marvel.domain.models.Character
import com.example.marvel.domain.usecases.ChangeFavoriteCharacterBaseUseCase
import com.example.marvel.domain.usecases.GetDetailCharacterBaseUseCase
import com.example.marvel.ui.base.BaseViewModel
import com.example.marvel.utils.DispatcherFactory
import com.example.marvel.utils.SingleEvent
import com.task.data.error.DEFAULT_ERROR
import com.task.usecase.errors.ErrorManager
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(
    private val dispatcherFactory: DispatcherFactory,
    private val getCharactersDetailUseCase: GetDetailCharacterBaseUseCase,
    private val changeFavoriteCharacterUseCase: ChangeFavoriteCharacterBaseUseCase,
    private val errorManager: ErrorManager
) : BaseViewModel(dispatcherFactory) {

    val data : MutableState<Resource<Character>> = mutableStateOf(Resource.Loading())

    fun getCharacter(id: Int) {
        Log.d("CALLVIEWMODEL",id.toString())
        launch {
            withContext(dispatcherFactory.getIO()) {
                getCharactersDetailUseCase(id).onStart {
                    data.value = Resource.Loading()
                }
                    .catch {
                        data.value = Resource.DataError(DEFAULT_ERROR) }.collect {
                        data.value = it
                    }
            }
        }
    }

    fun showToastMessage(context: Context) {
        val error = errorManager.getError(data.value.errorCode?: DEFAULT_ERROR)
        Toast.makeText(context, error.description, Toast.LENGTH_SHORT).show()
    }

    fun changeFavoriteCharacter(character: Character) {
        launch {
            withContext(dispatcherFactory.getIO()) {
                changeFavoriteCharacterUseCase(character).distinctUntilChanged().collect()
            }
        }
    }
}