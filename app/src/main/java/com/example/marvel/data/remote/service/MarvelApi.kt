package com.example.marvel.data.remote.service

import MarvelBase
import com.example.marvel.data.remote.service.ConstantsUrls.CHARACTERS_URL
import com.example.marvel.data.remote.service.ConstantsUrls.CHARACTER_URL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MarvelApi {

    @GET(CHARACTERS_URL)
    suspend fun getCharacters(
    ): Response<MarvelBase>

    @GET(CHARACTER_URL)
    suspend fun getCharacterById(
        @Path("characterId") characterId: Int
    ): Response<MarvelBase>

}