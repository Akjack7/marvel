package com.example

import com.example.marvel.utils.DispatcherFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class TestAppDispatcherFactory : DispatcherFactory {
    override fun getMain(): CoroutineContext  = StandardTestDispatcher()
    override fun getIO(): CoroutineContext = StandardTestDispatcher()
}