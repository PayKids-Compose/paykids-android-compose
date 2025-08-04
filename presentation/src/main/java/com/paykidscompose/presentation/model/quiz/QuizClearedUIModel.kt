package com.paykidscompose.presentation.model.quiz

import com.paykidscompose.presentation.model.UIModel

data class QuizClearedUIModel(
    val message: String,
    val isCleared: Boolean
): UIModel()
