package com.paykidscompose.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.paykidscompose.common.usecase.authentication.LoginUseCase
import com.paykidscompose.presentation.screen.login.LoginViewModel

class LoginViewModelFactory(
    private val loginUseCase: LoginUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(loginUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}