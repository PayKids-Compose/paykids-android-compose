package com.paykidscompose.common.usecase.base

abstract class SuspendUseCase<in Params, out T> {
    protected abstract suspend fun execute(params: Params? = null): T

    suspend operator fun invoke(params: Params? = null): T = execute(params)
}