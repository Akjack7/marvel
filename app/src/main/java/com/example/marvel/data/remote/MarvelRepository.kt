package com.example.marvel.data.remote

import Results
import com.example.marvel.data.remote.service.MarvelApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MarvelRepository(private val marvelApi: MarvelApi) : IMarvelRepository {

    override fun getCharacters(): Flow<List<Results>> = flow {
        emit(marvelApi.getCharacters().data.results)
    }

    override fun getCharacterById(id: Int): Flow<Results> = flow {
        emit(marvelApi.getCharacterById(id).data.results.first())
    }
}