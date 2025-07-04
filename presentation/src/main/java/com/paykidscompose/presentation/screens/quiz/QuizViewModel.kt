package com.paykidscompose.presentation.screens.quiz

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paykidscompose.presentation.dummy.DummyQuiz
import com.paykidscompose.presentation.model.QuizUIModel
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {
    private val _quizzes = mutableStateListOf<QuizUIModel>()

    var currentIndex by mutableStateOf(0)
        private set

    var currentQuiz by mutableStateOf(_quizzes.firstOrNull())
        private set

    fun loadQuiz(stageNumber: Int) {
        viewModelScope.launch {
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
            }
            _quizzes.addAll(quizzes)
            currentIndex = 0
            currentQuiz = _quizzes[currentIndex]
        }
    }

    fun moveToNext() {
        if (currentIndex < _quizzes.size - 1) {
            currentIndex++
            currentQuiz = _quizzes[currentIndex]
        }
    }

    fun isLastQuiz(): Boolean {
        return currentIndex == _quizzes.size - 1
    }
}