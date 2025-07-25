package com.paykidscompose.common.usecase.authentication

import com.paykidscompose.common.enums.EntryPoint
import com.paykidscompose.common.model.TokenManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class CheckUserCompletionStatusUseCase(
    private val tokenManager: TokenManager
) {
    operator fun invoke(): Flow<EntryPoint> {
        return tokenManager.getToken().combine(tokenManager.getIsRegistered()) { token, isRegistered ->
            if (token.isBlank()) {
                EntryPoint.LOGIN
            } else if (isRegistered) {
                EntryPoint.HOME
            } else {
                EntryPoint.ONBOARDING
            }
        }
    }
}