package com.example.marvel.domain.usecases

import com.example.marvel.data.local.ILocalRepository
import com.example.marvel.data.remote.IMarvelRepository
import com.example.marvel.domain.models.Character
import com.example.marvel.domain.models.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

typealias GetDetailCharacterBaseUseCase = BaseUseCase<Int, Flow<Character>>

class GetDetailCharacterUseCase(
    private val remoteRepository: IMarvelRepository,
    private val localRepository: ILocalRepository
) : GetDetailCharacterBaseUseCase {

    override suspend fun invoke(params: Int): Flow<Character> = flow {
        localRepository.getCharacterById(params).collect {
            if (it != null) {
                val character = it.copy().toDomain().apply { isFavorite = true }
                emit(character)
            } else {
                remoteRepository.getCharacterById(params).collect { result ->
                    emit(result.toDomain())
                }
            }
        }
    }
}