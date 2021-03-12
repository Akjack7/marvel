package com.example.marvel.domain

import MarvelBase

class MarvelRepository(private val marvelApi: MarvelApi) {

    suspend fun getCharacters(): MarvelBase {
        return marvelApi.getCharacters()
    }
}