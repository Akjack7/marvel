package com.example.marvel.data.local

import com.example.marvel.data.Resource
import com.example.marvel.data.local.database.CharacterDao
import com.example.marvel.data.local.dto.CharacterDaoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class LocalRepository(private val characterDao: CharacterDao) : ILocalRepository {

    override fun insert(character: CharacterDaoModel) {
        characterDao.insert(character)
    }

    override fun getCharacterById(id: Int): Flow<Resource<CharacterDaoModel?>> = flow {
        characterDao.loadCharacterById(id)?.collect {
            emit(Resource.Success(data = it))
        }
    }

    override fun deleteCharacter(character: CharacterDaoModel) {
        characterDao.delete(character)
    }
}