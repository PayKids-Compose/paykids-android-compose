package com.paykidscompose.common.usecase.base

import kotlinx.coroutines.flow.Flow

abstract class FlowUseCase<in Params, out T> {
    protected abstract fun execute(params: Params? = null): Flow<T>

    operator fun invoke(params: Params? = null): Flow<T> = execute(params)
}