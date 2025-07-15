package com.paykidscompose.data.network.service.authentication

import android.content.Context
import android.content.Intent
import android.util.Log
import com.kakao.sdk.user.UserApiClient
import com.paykidscompose.data.model.Token
import com.paykidscompose.data.util.getCustomParcelableExtra
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class KakaoLoginService(
    private val appContext: Context
) : SocialLoginService {
    override suspend fun login(): Token = suspendCancellableCoroutine { continuation ->
        KakaoLoginHelperActivity.startActivityForResult(
            context = appContext,
            intent = Intent(appContext, KakaoLoginHelperActivity::class.java)
        ) { _: Int, data: Intent? ->
            val token = data?.getCustomParcelableExtra(
                name = KakaoLoginHelperActivity.TOKEN_PARAM,
                clazz = Token::class.java
            )

            if(token == null) {
                continuation.resumeWithException(IllegalStateException())
                return@startActivityForResult
            }

            continuation.resume(token)
        }
    }

    override suspend fun logout(): Result<Unit> = suspendCancellableCoroutine { continuation ->
        UserApiClient.instance.logout { error ->
            if(error != null) {
                Log.e(TAG, "카카오 로그아웃 실패", error)
                continuation.resume(Result.failure(error))
            } else {
                Log.i(TAG, "카카오 로그아웃 성공")
                continuation.resume(Result.success(Unit))
            }
        }
    }

    companion object {
        private const val TAG = "KakaoLoginService"
    }
}