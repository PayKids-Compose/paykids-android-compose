package com.paykidscompose.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.paykidscompose.common.usecase.user.SaveNicknameUseCase
import com.paykidscompose.presentation.screens.login.nickname.LoginNicknameViewModel

class LoginNicknameViewModelFactory(
    private val saveNicknameUseCase: SaveNicknameUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginNicknameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginNicknameViewModel(saveNicknameUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}