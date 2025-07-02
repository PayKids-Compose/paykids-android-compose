package com.paykidscompose.presentation.model

import androidx.annotation.DrawableRes
import com.paykidscompose.presentation.model.type.QuizType

data class QuizUIModel(
    val stage: Int,
    val number: Int,
    val quizType: QuizType,
    val question: String,
    val choices: List<Pair<String, String>>?,
    val answer: String,
    @DrawableRes
    val imageUrl: List<Int>?,
    val totalCount: Int = 0
): UIModel()