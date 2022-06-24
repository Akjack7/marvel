package com.example.marvel.presentation

import androidx.lifecycle.ViewModel
import com.example.marvel.utils.DispatcherFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(
    private val dispatcherFactory: DispatcherFactory
) : ViewModel(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = dispatcherFactory.getMain() + job

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}