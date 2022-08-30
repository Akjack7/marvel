package com.example.marvel.data.remote.service

import Results
import com.example.marvel.data.Resource

internal interface IMarvelDataSource {
    suspend fun requestCharacters(): Resource<List<Results>>
    suspend fun requestCharacter(id: Int): Resource<Results>
}