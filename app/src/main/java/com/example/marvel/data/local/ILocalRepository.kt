package com.example.marvel.data.local

import com.example.marvel.data.Resource
import com.example.marvel.data.local.dto.CharacterDaoModel
import kotlinx.coroutines.flow.Flow

interface ILocalRepository {

    fun insert(character: CharacterDaoModel)

    fun getCharacterById(id: Int): Flow<Resource<CharacterDaoModel?>>

    fun deleteCharacter(character: CharacterDaoModel)
}