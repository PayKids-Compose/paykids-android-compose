package com.paykidscompose.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paykidscompose.app.ui.theme.PayKidsComposeTheme
import com.paykidscompose.common.di.ApplicationContainerProvider
import com.paykidscompose.common.usecase.authentication.CheckUserCompletionStatusUseCase
import com.paykidscompose.data.model.TokenManagerImpl
import com.paykidscompose.presentation.factory.LoginNicknameViewModelFactory
import com.paykidscompose.presentation.factory.LoginViewModelFactory
import com.paykidscompose.presentation.factory.MyInfoViewModelFactory
import com.paykidscompose.presentation.factory.MyPageViewModelFactory
import com.paykidscompose.presentation.navigation.PayKidsApp
import com.paykidscompose.presentation.screens.login.LoginViewModel
import com.paykidscompose.presentation.screens.login.nickname.LoginNicknameViewModel
import com.paykidscompose.presentation.screens.mypage.MyPageViewModel
import com.paykidscompose.presentation.screens.mypage.info.MyInfoViewModel

class MainActivity : ComponentActivity() {
    private val provider by lazy {
        (applicationContext as ApplicationContainerProvider).provideAppContainer()
    }
    private val userCompletionStatusUseCase = CheckUserCompletionStatusUseCase(TokenManagerImpl())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isLogin by userCompletionStatusUseCase().collectAsStateWithLifecycle(
                intent.getBooleanExtra(
                    "isLogin",
                    false
                )
            )
            PayKidsComposeTheme {
                PayKidsApp(
                    isLogin,
                    loginViewModel = ViewModelProvider(
                        this,
                        LoginViewModelFactory(provider.loginUseCase)
                    )[LoginViewModel::class.java],
                    loginNicknameViewModel = ViewModelProvider(
                        this,
                        LoginNicknameViewModelFactory(provider.saveNicknameUseCase)
                    )[LoginNicknameViewModel::class.java],
                    myInfoViewModel = ViewModelProvider(
                        this,
                        MyInfoViewModelFactory(
                            provider.getUserUseCase,
                            provider.saveNicknameUseCase,
                            provider.deleteUserUseCase
                        )
                    )[MyInfoViewModel::class.java],
                    myPageViewModel = ViewModelProvider(
                        this,
                        MyPageViewModelFactory(provider.getUserUseCase, provider.logoutUseCase)
                    )[MyPageViewModel::class.java]
                )
            }
        }
    }
}