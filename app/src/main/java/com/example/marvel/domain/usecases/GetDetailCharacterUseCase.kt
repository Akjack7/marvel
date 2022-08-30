package com.example.marvel.domain.usecases

import com.example.marvel.data.Resource
import com.example.marvel.data.local.ILocalRepository
import com.example.marvel.data.remote.IMarvelRepository
import com.example.marvel.domain.models.Character
import com.example.marvel.domain.models.toDomain
import com.task.data.error.DEFAULT_ERROR
import com.task.data.error.NO_RESULTS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

typealias GetDetailCharacterBaseUseCase = BaseUseCase<Int, Flow<Resource<Character>>>

class GetDetailCharacterUseCase(
    private val remoteRepository: IMarvelRepository,
    private val localRepository: ILocalRepository
) : GetDetailCharacterBaseUseCase {

    override suspend fun invoke(params: Int): Flow<Resource<Character>> = flow {
        localRepository.getCharacterById(params).collect {
            if (it.data != null) {
                val character = it.data.copy().toDomain().apply { isFavorite = true }
                emit(Resource.Success(data = character))
            } else {
                remoteRepository.getCharacterById(params).collect { result ->
                    if (result.data != null) {
                        emit(Resource.Success(data = result.data.toDomain()))
                    } else {
                        emit(Resource.DataError(errorCode = result.errorCode ?: NO_RESULTS))
                    }
                }
            }
        }
    }
}