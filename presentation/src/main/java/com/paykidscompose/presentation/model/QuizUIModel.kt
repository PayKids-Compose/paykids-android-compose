package com.paykidscompose.presentation.model

import com.paykidscompose.common.enums.QuizType

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
