package com.task.usecase.errors

import com.task.data.error.Error
import com.task.data.error.mapper.ErrorMapper

class ErrorManager (private val errorMapper: ErrorMapper) : ErrorUseCase {
    override fun getError(errorCode: Int): Error {
        return Error(code = errorCode, description = errorMapper.errorsMap.getValue(errorCode))
    }
}
