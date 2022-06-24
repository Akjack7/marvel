package com.example.marvel.data.apis

import MarvelBase
import com.example.marvel.data.apis.ConstantsUrls.CHARACTERS_URL
import retrofit2.http.GET

interface MarvelApi {

    //todo pagination
    @GET(CHARACTERS_URL)
    suspend fun getCharacters(
    ): MarvelBase
}