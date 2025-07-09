package com.paykidscompose.data.repositories

import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.data.database.PayKidsPreference
import com.paykidscompose.data.model.BaseResponse
import com.paykidscompose.data.model.user.LoginDTO
import com.paykidscompose.data.network.NetworkModule
import com.paykidscompose.data.network.service.AuthApiService
import androidx.core.content.edit

class AuthRepositoryImpl(private val authApiService: AuthApiService = NetworkModule.provideAuthApiService()) {
    suspend fun fetchRefreshToken(refreshToken: String): DataResourceResult<BaseResponse<LoginDTO>> {
        runCatching { authApiService.fetchRefreshToken(refreshToken) }
            .getOrElse { return DataResourceResult.Failure(it) }
            .let { return DataResourceResult.Success(it) }
    }

    suspend fun fetchLoginToken(idToken: String): DataResourceResult<Unit> {
        runCatching {
            val token = authApiService.fetchLoginToken(idToken)

            PayKidsPreference.getInstance().edit {
                putString("accessToken", token.data.accessToken)
                    .putString("refreshToken", token.data.refreshToken)
            }
        }.getOrElse { return DataResourceResult.Failure(it) }
            .let { return DataResourceResult.Success(Unit) }
    }
}