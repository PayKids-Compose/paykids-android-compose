package com.paykidscompose.common.result

import com.paykidscompose.common.exception.PayKidsException

sealed interface DataResourceResult<out T> {
    data object DummyConstructor : DataResourceResult<Nothing>
    data object Loading : DataResourceResult<Nothing>
    data class Success<out T>(
        val data: T
    ) : DataResourceResult<T>
    data class Failure(
        val exception: PayKidsException
    ) : DataResourceResult<Nothing>
}