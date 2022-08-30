package com.task.data.error.mapper

import android.content.Context
import com.example.marvel.R
import com.task.data.error.*


class ErrorMapper (private val context: Context) : ErrorMapperSource {

    override fun getErrorString(errorId: Int): String {
        return context.getString(errorId)
    }

    override val errorsMap: Map<Int, String>
        get() = mapOf(
            Pair(NO_INTERNET_CONNECTION, getErrorString(R.string.no_internet)),
            Pair(NETWORK_ERROR, getErrorString(R.string.network_error)),
            Pair(NO_RESULTS, getErrorString(R.string.network_error)),
        ).withDefault { getErrorString(R.string.network_error) }
}
