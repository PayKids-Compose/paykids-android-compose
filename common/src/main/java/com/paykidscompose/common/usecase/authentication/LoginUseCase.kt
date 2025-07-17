package com.paykidscompose.common.usecase.authentication

import com.paykidscompose.common.repositories.AuthRepository
import com.paykidscompose.common.result.DataResourceResult

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): DataResourceResult<Unit> = authRepository.fetchLoginToken()
}