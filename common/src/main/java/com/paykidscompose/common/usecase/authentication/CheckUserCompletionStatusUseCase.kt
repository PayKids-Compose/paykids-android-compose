package com.paykidscompose.common.usecase.authentication

import com.paykidscompose.common.enums.EntryPoint
import com.paykidscompose.common.model.TokenManager
import com.paykidscompose.common.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class CheckUserCompletionStatusUseCase(
    private val tokenManager: TokenManager
) : FlowUseCase<Unit, EntryPoint>() {
    override fun execute(params: Unit?): Flow<EntryPoint> {
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