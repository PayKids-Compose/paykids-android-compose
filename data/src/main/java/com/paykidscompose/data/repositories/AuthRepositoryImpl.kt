package com.paykidscompose.data.repositories

import android.util.Log
import androidx.core.content.edit
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.data.database.PayKidsPreference
import com.paykidscompose.data.model.user.LoginDTO
import com.paykidscompose.data.network.NetworkModule
import com.paykidscompose.data.network.service.AuthApiService
import com.paykidscompose.data.network.service.authentication.KakaoLoginService
import com.paykidscompose.data.util.ACCESS_TOKEN
import com.paykidscompose.data.util.REFRESH_TOKEN
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val authApiService: AuthApiService = NetworkModule.provideAuthApiService(),
    private val kakaoLoginService: KakaoLoginService
) {
    suspend fun fetchRefreshToken(): DataResourceResult<LoginDTO> {
        return runCatching {
            val token = PayKidsPreference.getInstance().getString(REFRESH_TOKEN, null)
                ?: throw IllegalStateException("저장소에 REFRESH TOKEN이 없습니다.")
            authApiService.fetchRefreshToken(token)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    suspend fun fetchLoginToken(): DataResourceResult<Unit> {
        return runCatching {
            val token = kakaoLoginService.login()
            val response = authApiService.fetchLoginToken(token.idToken)
            withContext(Dispatchers.IO) {
                PayKidsPreference.getInstance().edit {
                    putString(ACCESS_TOKEN, response.data.accessToken)
                    putString(REFRESH_TOKEN, response.data.refreshToken)
                }
            }
        }.fold(
            onSuccess = { DataResourceResult.Success(Unit) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    suspend fun logout(): DataResourceResult<Boolean> {
        val result = kakaoLoginService.logout()
        return result.fold(
            onSuccess = { DataResourceResult.Success(true) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }
}