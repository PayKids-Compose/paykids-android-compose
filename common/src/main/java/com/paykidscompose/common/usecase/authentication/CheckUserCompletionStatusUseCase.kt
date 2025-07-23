package com.paykidscompose.common.usecase.authentication

import com.paykidscompose.common.model.TokenManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CheckUserCompletionStatusUseCase(
    private val tokenManager: TokenManager
) {
    operator fun invoke(): Flow<Boolean> {
        return tokenManager.getToken().map {
            it.isNotBlank()
        }
    }
}