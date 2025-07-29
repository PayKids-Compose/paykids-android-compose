package com.paykidscompose.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.paykidscompose.common.usecase.quiz.GetAllQuizzesUseCase
import com.paykidscompose.common.usecase.quiz.GetCheckAnswerUseCase
import com.paykidscompose.common.usecase.quiz.GetCheckStageUseCase
import com.paykidscompose.presentation.screens.quiz.QuizViewModel

class QuizViewModelFactory(
    private val getAllQuizzesUseCase: GetAllQuizzesUseCase,
    private val getCheckAnswerUseCase: GetCheckAnswerUseCase,
    private val getCheckStageUseCase: GetCheckStageUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuizViewModel(getAllQuizzesUseCase, getCheckAnswerUseCase, getCheckStageUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}