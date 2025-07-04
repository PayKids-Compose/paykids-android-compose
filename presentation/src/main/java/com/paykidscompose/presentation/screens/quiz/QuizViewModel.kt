package com.paykidscompose.presentation.screens.quiz

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.paykidscompose.presentation.dummy.DummyQuiz
import com.paykidscompose.presentation.model.QuizUIModel

class QuizViewModel : ViewModel() {
    private val _quizzes = mutableStateListOf<QuizUIModel>()
    val quizzes: List<QuizUIModel> = _quizzes

    var currentIndex by mutableStateOf(0)
        private set

    var currentQuiz by mutableStateOf<QuizUIModel?>(null)
        private set

    fun loadQuiz(stageNumber: Int) {
        _quizzes.clear()
        _quizzes.addAll(DummyQuiz.getQuizzes(stageNumber))
        currentIndex = 0
        currentQuiz = _quizzes.getOrNull(currentIndex)
    }

    fun moveToNext() {
        if (currentIndex < _quizzes.size - 1) {
            currentIndex++
            currentQuiz = _quizzes.getOrNull(currentIndex)
        }
    }

    fun isLastQuiz(): Boolean {
        return currentIndex == _quizzes.size - 1
    }
}