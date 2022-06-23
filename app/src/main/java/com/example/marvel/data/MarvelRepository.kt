package com.example.marvel.data

import MarvelBase

class MarvelRepository(private val marvelApi: MarvelApi) {

    suspend fun getCharacters(): MarvelBase {
        return marvelApi.getCharacters()
    }
}