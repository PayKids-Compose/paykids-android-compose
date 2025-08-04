package com.paykidscompose.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.paykidscompose.common.usecase.study.GetChatResponseUseCase
import com.paykidscompose.presentation.screens.study.StudyViewModel

class StudyViewModelFactory(
    private val getChatResponseUseCase: GetChatResponseUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudyViewModel::class.java)) {
            return StudyViewModel(getChatResponseUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}