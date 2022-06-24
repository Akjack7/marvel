package com.example.marvel.data.remote

import Results
import kotlinx.coroutines.flow.Flow

interface IMarvelRepository {
    fun getCharacters(): Flow<List<Results>>
    fun getCharacterById(id: Int): Flow<Results>
}