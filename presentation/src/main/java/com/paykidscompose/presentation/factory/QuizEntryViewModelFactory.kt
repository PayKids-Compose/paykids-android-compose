package com.paykidscompose.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.paykidscompose.common.usecase.quiz.GetWrongAnswerStatusUseCase
import com.paykidscompose.presentation.screens.quiz.QuizEntryViewModel

class QuizEntryViewModelFactory(
    private val getWrongAnswerStatusUseCase: GetWrongAnswerStatusUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizEntryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuizEntryViewModel(getWrongAnswerStatusUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}