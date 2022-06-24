package com.example.marvel.data.local

import com.example.marvel.data.local.database.CharacterDao
import com.example.marvel.domain.models.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class LocalRepository(private val characterDao: CharacterDao) : ILocalRepository {

    override fun insert(character: CharacterDaoModel) {
        characterDao.insert(character)
    }

    override fun getCharacterById(id: Int): Flow<CharacterDaoModel?> = flow {
        characterDao.loadCharacterById(id)?.collect {
            emit(it)
        }
    }

    override fun deleteCharacter(character: CharacterDaoModel) {
        characterDao.delete(character)
    }
}