package com.example.marvel.data.remote.apis

import MarvelBase
import Results
import com.example.marvel.data.remote.apis.ConstantsUrls.CHARACTERS_URL
import com.example.marvel.data.remote.apis.ConstantsUrls.CHARACTER_URL
import retrofit2.http.GET
import retrofit2.http.Path

interface MarvelApi {

    @GET(CHARACTERS_URL)
    suspend fun getCharacters(
    ): MarvelBase

    @GET(CHARACTER_URL)
    suspend fun getCharacterById(
        @Path("characterId") characterId: Int
    ): MarvelBase

}