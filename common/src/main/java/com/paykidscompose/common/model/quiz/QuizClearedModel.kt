package com.paykidscompose.common.model.quiz

import com.paykidscompose.common.model.Model

data class QuizClearedModel(
    val message: String,
    val isCleared: Boolean
): Model()
