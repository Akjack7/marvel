package com.example.marvel.data

import Results
import kotlinx.coroutines.flow.Flow

interface IMarvelRepository {
    fun getCharacters(): Flow<List<Results>>
}