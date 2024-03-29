package com.example.marvel.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class AppDispatcherFactory : DispatcherFactory {
    override fun getMain(): CoroutineContext = Dispatchers.Main
    override fun getIO(): CoroutineContext = Dispatchers.IO
}