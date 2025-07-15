package com.paykidscompose.data.network.service.authentication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User
import com.paykidscompose.data.model.Token


internal class KakaoLoginHelperActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            loginKakaoWeb(
                onSuccess = { finishActivityWithSuccessResult(it) },
                onFailure = { finishActivityWithFailureResult() }
            )
            return
        }

        loginKakaoTalk(
            onSuccess = { finishActivityWithSuccessResult(it) },
            onFailure = {
                loginKakaoWeb(
                    onSuccess = { finishActivityWithSuccessResult(it) },
                    onFailure = { finishActivityWithFailureResult() }
                )
            }
        )
    }

    private fun finishActivityWithSuccessResult(token: Token) {
        val intent = Intent().apply {
            putExtra(TOKEN_PARAM, token)
        }
        setResult(RESULT_OK, intent)
        activityResultListener?.invoke(RESULT_OK, intent)
        activityResultListener = null
        finish()
    }

    private fun finishActivityWithFailureResult() {
        setResult(RESULT_CANCELED)
        activityResultListener?.invoke(RESULT_CANCELED, null)
        activityResultListener = null
        finish()
    }

    private fun loginKakaoWeb(
        onSuccess: (Token) -> Unit,
        onFailure: (Throwable?) -> Unit,
    ) {
        try {
            UserApiClient.instance.loginWithKakaoAccount(
                context = this@KakaoLoginHelperActivity,
                callback = { token, error ->
                    if (error != null || token == null) {
                        Log.e(TAG, "카카오계정으로 로그인 실패", error)
                        onFailure(error)
                        return@loginWithKakaoAccount
                    }
                    UserApiClient.instance.me { user, error ->
                        if (user == null || error != null) {
                            Log.e(TAG, "카카오 유저정보 로드 실패", error)
                            onFailure(error)
                            return@me
                        }
                        onSuccess(handleCallbackToken(token, user))
                    }
                }
            )
        } catch (e: Exception) {
            Log.e(TAG, "loginKakaoWeb 예외 발생", e)
            onFailure(e)
        }
    }

    private fun loginKakaoTalk(
        onSuccess: (Token) -> Unit,
        onFailure: (Throwable?) -> Unit,
    ) {
        try {
            UserApiClient.instance.loginWithKakaoTalk(
                context = this@KakaoLoginHelperActivity,
                callback = { token: OAuthToken?, error: Throwable? ->
                    if (error != null || token == null) {
                        Log.e(TAG, "카카오톡 로그인 실패", error)
                        onFailure(error)
                        return@loginWithKakaoTalk
                    }
                    UserApiClient.instance.me { user, error ->
                        if (user == null || error != null) {
                            Log.e(TAG, "카카오 유저정보 로드 실패", error)
                            onFailure(error)
                            return@me
                        }
                        onSuccess(handleCallbackToken(token, user))
                    }
                }
            )
        } catch (e: Exception) {
            Log.e(TAG, "loginKakaoTalk 예외 발생", e)
            onFailure(e)
        }
    }

    private fun handleCallbackToken(
        token: OAuthToken,
        user: User?,
    ): Token {
        Log.i(TAG, "카카오계정으로 로그인 성공")
        return Token(
            userId = user?.id?.toString() ?: "null",
            profileUrl = user?.kakaoAccount?.profile?.profileImageUrl ?: "null",
            idToken = token.idToken ?: throw IllegalStateException("idToken이 존재하지 않습니다."),
            accessToken = token.accessToken,
            accessTokenExpiresAt = token.accessTokenExpiresAt,
            refreshToken = token.refreshToken,
            refreshTokenExpiresAt = token.refreshTokenExpiresAt
        )
    }

    companion object {
        private const val TAG = "KakaoLoginHelperActivity"
        internal const val TOKEN_PARAM = "token"

        private var activityResultListener: ((resultCode: Int, data: Intent?) -> Unit)? = null

        fun startActivityForResult(
            context: Context,
            intent: Intent,
            resultListener: (resultCode: Int, data: Intent?) -> Unit,
        ) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION)
            context.startActivity(intent)
            activityResultListener = resultListener
        }
    }
}