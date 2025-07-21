package com.paykidscompose.common.usecase.authentication

import com.paykidscompose.common.repositories.AuthRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase

class LoginUseCase(
    private val authRepository: AuthRepository
) : SuspendUseCase<Unit, DataResourceResult<Unit>>() {
    override suspend fun execute(params: Unit?): DataResourceResult<Unit> = authRepository.fetchLoginToken()
}