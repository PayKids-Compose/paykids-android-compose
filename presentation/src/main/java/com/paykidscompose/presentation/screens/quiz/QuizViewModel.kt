package com.paykidscompose.presentation.screens.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.presentation.base.UIState
import com.paykidscompose.presentation.dummy.DummyQuiz
import com.paykidscompose.presentation.model.QuizUIModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(QuizUIState())
    val uiState = _uiState.asStateFlow()

    fun loadQuiz(stageNumber: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            delay(500)

            runCatching {
                val quizzes = DummyQuiz.getQuizzes(stageNumber).map { quiz ->
                    QuizUIModel(
                        quiz.stage,
                        quiz.number,
                        quiz.quizType,
                        quiz.question,
                        quiz.choices,
                        quiz.answer,
                        quiz.imageUrl,
                        quiz.totalCount
                    )
                }.toMutableList()

                // 예외 상황 처리: 퀴즈가 비어 있으면 예외 발생
                require(quizzes.isNotEmpty())

                quizzes
            }.onSuccess { quizzes ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        quizzes = quizzes,
                        currentIndex = 0,
                        totalCount = quizzes.first().totalCount
                    )
                }
            }.onFailure { e ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = PayKidsException.SnackBarException(message = e.message?: "")
                    )
                }
            }
        }
    }

    fun moveToNext() {
        _uiState.update {
            if (it.currentIndex < it.quizzes.lastIndex) {
                it.copy(currentIndex = it.currentIndex + 1)
            } else it
        }
    }

    fun isLastQuiz(): Boolean {
        return uiState.value.currentIndex == uiState.value.quizzes.lastIndex
    }
}

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