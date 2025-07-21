package com.paykidscompose.presentation.model

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.presentation.base.UIState
import com.paykidscompose.presentation.model.type.QuizType

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

data class QuizUIState(
    override val isLoading: Boolean = false,
    override val error: PayKidsException? = null,
    val quizzes: MutableList<QuizUIModel> = mutableListOf(),
    val totalCount: Int = 0,
    val currentIndex: Int = 0,
): UIState() {
    val currentQuiz: QuizUIModel?
        get() = quizzes.getOrNull(currentIndex)
}
