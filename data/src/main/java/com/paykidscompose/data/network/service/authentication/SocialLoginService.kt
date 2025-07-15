package com.paykidscompose.data.network.service.authentication

import com.paykidscompose.data.model.Token

internal interface SocialLoginService {
    suspend fun login(): Token
    suspend fun logout(): Result<Unit>
}