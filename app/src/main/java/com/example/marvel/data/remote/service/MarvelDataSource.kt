package com.example.marvel.data.remote.service

import MarvelBase
import Results
import com.example.marvel.data.Resource
import com.example.marvel.utils.Network
import com.task.data.error.NETWORK_ERROR
import com.task.data.error.NO_INTERNET_CONNECTION
import retrofit2.Response
import java.io.IOException

class MarvelDataSource(private val networkConnectivity: Network, private val service: MarvelApi) :
    IMarvelDataSource {

    override suspend fun requestCharacters(): Resource<List<Results>> {
        return when (val response = processCall(service::getCharacters)) {
            is List<*> -> {
                Resource.Success(data = response as List<Results>)
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }


    override suspend fun requestCharacter(id: Int): Resource<Results> {
        return when (val response = processCall { service::getCharacterById.invoke(id) }) {
            is List<*> -> {
                Resource.Success(data = (response as List<Results>).first())
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }


    private suspend fun processCall(responseCall: suspend () -> Response<MarvelBase>): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()?.data?.results
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }


}