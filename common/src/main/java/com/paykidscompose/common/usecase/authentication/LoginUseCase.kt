package com.paykidscompose.common.usecase.authentication

import com.paykidscompose.common.repository.AuthRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : SuspendUseCase<Unit, DataResourceResult<Boolean>>() {
    override suspend fun execute(params: Unit?): DataResourceResult<Boolean> = authRepository.fetchLoginToken()
}