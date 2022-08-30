package com.example.marvel.domain.usecases

import com.example.marvel.data.Resource
import com.example.marvel.data.remote.IMarvelRepository
import com.example.marvel.domain.models.Character
import com.example.marvel.domain.models.toDomain
import com.task.data.error.NO_RESULTS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

typealias GetCharactersBaseUseCase = BaseUseCase<Unit, Flow<Resource<List<Character>>>>

class GetCharactersUseCase(
    private val repository: IMarvelRepository
) : GetCharactersBaseUseCase {

    override suspend fun invoke(params: Unit): Flow<Resource<List<Character>>> = flow {
        repository.getCharacters().collect { results ->
            if (!results.data.isNullOrEmpty()) {
                emit(Resource.Success(data = results.data.map { it.toDomain() }))
            } else {
                emit(Resource.DataError(errorCode = results.errorCode ?: NO_RESULTS))
            }
        }
    }
}