package com.paykidscompose.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paykidscompose.app.SplashActivity.Companion.LOGIN_STATUS
import com.paykidscompose.app.ui.theme.PayKidsComposeTheme
import com.paykidscompose.common.enums.EntryPoint
import com.paykidscompose.common.usecase.authentication.CheckUserCompletionStatusUseCase
import com.paykidscompose.presentation.navigation.PayKidsApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var userCompletionStatusUseCase : CheckUserCompletionStatusUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val loginStatus by userCompletionStatusUseCase().collectAsStateWithLifecycle(
                EntryPoint.valueOf(
                    intent.getStringExtra(
                        LOGIN_STATUS
                    ) ?: EntryPoint.LOGIN.name
                )
            )

            PayKidsComposeTheme {
                PayKidsApp(
                    loginStatus
                )
            }
        }
    }
}