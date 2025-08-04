package com.paykidscompose.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.paykidscompose.common.usecase.study.GetChatResponseUseCase
import com.paykidscompose.common.usecase.user.GetUserUseCase
import com.paykidscompose.presentation.screens.study.StudyViewModel

class StudyViewModelFactory(
    private val getChatResponseUseCase: GetChatResponseUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudyViewModel::class.java)) {
            return StudyViewModel(getChatResponseUseCase, getUserUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}