package com.paykidscompose.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.paykidscompose.app.databinding.ActivitySplashBinding
import com.paykidscompose.common.usecase.authentication.CheckUserCompletionStatusUseCase
import com.paykidscompose.data.model.AuthStatusManagerImpl
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashActivity : ComponentActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val userCompletionStatusUseCase = CheckUserCompletionStatusUseCase(AuthStatusManagerImpl)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            Log.d(TAG, "초기화 작업 시작!")
            val delay = async { delay(1500L) }
            val loginStatusDeferred = async { userCompletionStatusUseCase().first() }

            delay.await()
            val loginStatus = loginStatusDeferred.await()

            Log.d(TAG, "초기화 작업 완료! ${loginStatus.name}")

            startActivity(Intent(this@SplashActivity, MainActivity::class.java).apply {
                putExtra(LOGIN_STATUS, loginStatus.name)
            })
            finish()
        }
    }

    companion object {
        private const val TAG = "Splash Activity"
        const val LOGIN_STATUS = "Login Status"
    }
}