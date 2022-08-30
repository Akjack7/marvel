package com.example.marvel.data.remote

import Results
import com.example.marvel.data.Resource
import com.example.marvel.data.remote.service.MarvelApi
import com.example.marvel.data.remote.service.MarvelDataSource
import com.example.marvel.utils.DispatcherFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MarvelRepository(private val dataSource: MarvelDataSource,private val dispatcherFactory: DispatcherFactory) : IMarvelRepository {

    override fun getCharacters(): Flow <Resource<List<Results>>> = flow {
        emit(dataSource.requestCharacters())
    }.flowOn(dispatcherFactory.getIO())

    override fun getCharacterById(id: Int): Flow <Resource<Results>> = flow {
        emit(dataSource.requestCharacter(id))
    }.flowOn(dispatcherFactory.getIO())
}