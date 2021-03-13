package com.example.marvel.domain

import MarvelBase
import retrofit2.http.GET

interface MarvelApi {

    //todo pagination
    @GET("/v1/public/characters")
    suspend fun getCharacters(
    ): MarvelBase
}