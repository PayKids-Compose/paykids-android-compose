package com.paykidscompose.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.paykidscompose.common.usecase.user.DeleteUserUseCase
import com.paykidscompose.common.usecase.user.GetUserUseCase
import com.paykidscompose.common.usecase.user.SaveNicknameUseCase
import com.paykidscompose.presentation.screens.mypage.info.MyInfoViewModel

class MyInfoViewModelFactory(
    private val getUserUseCase: GetUserUseCase,
    private val saveNicknameUseCase: SaveNicknameUseCase, // ReplaceNicknameUseCase 타입으로 바꾸기
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyInfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyInfoViewModel(getUserUseCase, saveNicknameUseCase, deleteUserUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}