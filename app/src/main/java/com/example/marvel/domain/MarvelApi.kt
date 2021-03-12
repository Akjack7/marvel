package com.example.marvel.domain

import Data
import MarvelBase
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
    ): MarvelBase
}