package com.example.marvel.utils

import kotlin.coroutines.CoroutineContext

interface DispatcherFactory {
    fun getMain(): CoroutineContext
    fun getIO(): CoroutineContext
}