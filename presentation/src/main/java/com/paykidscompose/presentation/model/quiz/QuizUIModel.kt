package com.paykidscompose.presentation.model.quiz

import com.paykidscompose.common.enums.QuizType
import com.paykidscompose.presentation.model.UIModel

data class QuizUIModel(
    val stage: Int,
    val number: Int,
    val quizType: QuizType,
    val question: String,
    val choices: List<String>?,
    val answer: String,
    val imageUrl: List<String>?,
    val totalCount: Int = 0
): UIModel()
