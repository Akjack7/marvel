package com.example.marvel.data

import Results
import com.example.marvel.data.apis.MarvelApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MarvelRepository(private val marvelApi: MarvelApi) : IMarvelRepository {

    override fun getCharacters(): Flow<List<Results>> = flow {
        emit(marvelApi.getCharacters().data.results)
    }
}