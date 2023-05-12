package com.example.marvel.data.local

import android.util.Log
import com.example.marvel.data.Resource
import com.example.marvel.data.local.database.CharacterDao
import com.example.marvel.data.local.dto.CharacterDaoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class LocalRepository(private val characterDao: CharacterDao) : ILocalRepository {

    override fun insert(character: CharacterDaoModel) {
        Log.d("Insert",character.id.toString())

        characterDao.insert(character)
    }

    override fun getCharacterById(id: Int): Flow<Resource<CharacterDaoModel?>> = flow {
        Log.d("GetById",id.toString())
        characterDao.loadCharacterById(id)?.collect {
            Log.d("GetByIdCollect",id.toString())
            emit(Resource.Success(data = it))
        }
    }

    override fun deleteCharacter(character: CharacterDaoModel) {
        Log.d("Delete",character.id.toString())

        characterDao.delete(character)
    }
}