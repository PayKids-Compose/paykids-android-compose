package com.paykidscompose.data.repositories

import androidx.core.content.edit
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.data.database.PayKidsPreference
import com.paykidscompose.data.model.user.LoginDTO
import com.paykidscompose.data.network.NetworkModule
import com.paykidscompose.data.network.service.AuthApiService
import com.paykidscompose.data.util.ACCESS_TOKEN
import com.paykidscompose.data.util.REFRESH_TOKEN

class AuthRepositoryImpl(private val authApiService: AuthApiService = NetworkModule.provideAuthApiService()) {
    suspend fun fetchRefreshToken(refreshToken: String): DataResourceResult<LoginDTO> {
        return runCatching {
            val header = "Bearer $refreshToken"
            authApiService.fetchRefreshToken(header)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    suspend fun fetchLoginToken(idToken: String): DataResourceResult<Unit> {
        return runCatching {
            val header = "Bearer $idToken"
            val token = authApiService.fetchLoginToken(header)
            PayKidsPreference.getInstance().edit {
                putString(ACCESS_TOKEN, token.data.accessToken)
                putString(REFRESH_TOKEN, token.data.refreshToken)
            }
        }.fold(
            onSuccess = { DataResourceResult.Success(Unit) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }
}