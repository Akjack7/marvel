package com.example.marvel.domain.usecases

import com.example.marvel.data.remote.IMarvelRepository
import com.example.marvel.domain.models.Character
import com.example.marvel.domain.models.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

typealias GetCharactersBaseUseCase = BaseUseCase<Unit, Flow<List<Character>>>

class GetCharactersUseCase(
    private val repository: IMarvelRepository
) : GetCharactersBaseUseCase {

    override suspend fun invoke(params: Unit): Flow<List<Character>> = flow {
        repository.getCharacters().collect { results ->
            emit(results.map { it.toDomain() })
        }
    }
}