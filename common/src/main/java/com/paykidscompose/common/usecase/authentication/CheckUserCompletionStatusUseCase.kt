package com.paykidscompose.common.usecase.authentication

import com.paykidscompose.common.enums.EntryPoint
import com.paykidscompose.common.model.AuthStatusManager
import com.paykidscompose.common.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckUserCompletionStatusUseCase @Inject constructor(
    private val authStatusManager: AuthStatusManager
) : FlowUseCase<Unit, EntryPoint>() {
    override fun execute(params: Unit?): Flow<EntryPoint> {
        return authStatusManager.getToken()
            .combine(authStatusManager.getIsRegistered()) { token, isRegistered ->
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