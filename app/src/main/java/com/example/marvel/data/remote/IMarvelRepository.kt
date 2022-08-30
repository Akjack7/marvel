package com.example.marvel.data.remote

import Results
import com.example.marvel.data.Resource
import kotlinx.coroutines.flow.Flow

interface IMarvelRepository {
    fun getCharacters(): Flow <Resource<List<Results>>>
    fun getCharacterById(id: Int): Flow <Resource<Results>>
}