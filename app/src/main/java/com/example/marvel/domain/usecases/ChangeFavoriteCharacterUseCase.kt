package com.example.marvel.domain.usecases

import android.util.Log
import com.example.marvel.data.local.ILocalRepository
import com.example.marvel.domain.models.Character
import com.example.marvel.domain.models.toDaoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

typealias ChangeFavoriteCharacterBaseUseCase = BaseUseCase<Character, Flow<Unit>>

class ChangeFavoriteCharacterUseCase(
    private val localRepository: ILocalRepository
) : ChangeFavoriteCharacterBaseUseCase {

    override suspend fun invoke(params: Character): Flow<Unit> = flow {
        Log.i("FAvorites use case", params.id.toString())
            if (params.isFavorite) {
                localRepository.deleteCharacter(params.toDaoModel())
            } else {
                localRepository.insert(params.toDaoModel())
            }
    }
}