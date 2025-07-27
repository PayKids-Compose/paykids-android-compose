package com.paykidscompose.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.paykidscompose.common.usecase.quiz.GetStageCountUseCase
import com.paykidscompose.common.usecase.quiz.GetStageNameUseCase
import com.paykidscompose.common.usecase.quiz.GetStageToGoUseCase
import com.paykidscompose.presentation.screens.home.HomeViewModel

class HomeViewModelFactory(
    private val getStageCountUseCase: GetStageCountUseCase,
    private val getStageToGoUseCase: GetStageToGoUseCase,
    private val getStageNameUseCase: GetStageNameUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(getStageCountUseCase, getStageToGoUseCase, getStageNameUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}