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

    data class LoadingState constructor(val status: Status, val msg: String? = null) {
        companion object {
            val LOADED = LoadingState(Status.SUCCESS)
            val LOADING = LoadingState(Status.RUNNING)
            fun error(msg: String?) = LoadingState(Status.FAILED, msg)
        }

        enum class Status {
            RUNNING,
            SUCCESS,
            FAILED
        }
    }
}