package com.paykidscompose.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.repository.AuthRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.data.model.AuthStatusManagerImpl
import com.paykidscompose.data.network.service.AuthApiService
import com.paykidscompose.data.network.service.authentication.KakaoLoginService
import com.paykidscompose.data.util.ACCESS_TOKEN
import com.paykidscompose.data.util.REFRESH_TOKEN
import com.paykidscompose.data.util.USER_REGISTERED
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService,
    private val kakaoLoginService: KakaoLoginService,
    private val authStatusManager: AuthStatusManagerImpl,
    private val preference: SharedPreferences
) : AuthRepository {
    override suspend fun fetchRefreshToken(): DataResourceResult<Unit> { // 이 함수는 완성본이 아닙니다.
        return runCatching {
            val token = preference.getString(REFRESH_TOKEN, null)
                ?: throw IllegalStateException("저장소에 REFRESH TOKEN이 없습니다.")
            authApiService.fetchRefreshToken(token)
        }.fold(
            onSuccess = { DataResourceResult.Success(Unit) },
            onFailure = { DataResourceResult.Failure(PayKidsException.ToastException(code = -1, message = it.message ?: "")) }
        )
    }

    override suspend fun fetchLoginToken(): DataResourceResult<Boolean> {
        return runCatching {
            val token = kakaoLoginService.login()
            authApiService.fetchLoginToken(token.idToken)
        }.fold(
            onSuccess = {
                preference.edit {
                    putString(ACCESS_TOKEN, it.data.accessToken)
                    putString(REFRESH_TOKEN, it.data.refreshToken)
                    putBoolean(USER_REGISTERED, it.data.isRegistered)
                }
                DataResourceResult.Success(it.data.isRegistered)
            },
            onFailure = { DataResourceResult.Failure(PayKidsException.ToastException(code = -1, message = it.message ?: "")) }
        )
    }

    override suspend fun logout(): DataResourceResult<Unit> {
        val result = kakaoLoginService.logout()
        return result.fold(
            onSuccess = {
                preference.edit {
                    remove(ACCESS_TOKEN)
                    remove(REFRESH_TOKEN)
                    remove(USER_REGISTERED)
                }
                authStatusManager.notifyLoginScreenNav()
                DataResourceResult.Success(Unit)
            },
            onFailure = { DataResourceResult.Failure(PayKidsException.ToastException(code = -1, message = it.message ?: "")) }
        )
    }
}