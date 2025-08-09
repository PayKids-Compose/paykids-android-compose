package com.paykidscompose.presentation.screen.quiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paykidscompose.common.enums.QuizClearType
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.quiz.GetAllQuizzesUseCase
import com.paykidscompose.common.usecase.quiz.GetCheckAnswerUseCase
import com.paykidscompose.common.usecase.quiz.GetCheckStageUseCase
import com.paykidscompose.common.usecase.quiz.GetWrongAnswerQuizzesUseCase
import com.paykidscompose.presentation.base.UIState
import com.paykidscompose.presentation.mapper.quiz.QuizClearedUIModelMapper
import com.paykidscompose.presentation.mapper.quiz.QuizUIModelMapper
import com.paykidscompose.presentation.model.quiz.QuizUIModel
import com.paykidscompose.presentation.model.type.QuizResultType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuizViewModel(
    private val getAllQuizzesUseCase: GetAllQuizzesUseCase,
    private val getWrongAnswerQuizzesUseCase: GetWrongAnswerQuizzesUseCase,
    private val getCheckAnswerUseCase: GetCheckAnswerUseCase,
    private val getCheckStageUseCase: GetCheckStageUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(QuizUIState())
    val uiState = _uiState.asStateFlow()

    fun loadQuizzes(stageNumber: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            getAllQuizzesUseCase(GetAllQuizzesUseCase.Params(stageNumber))
                .collect { result ->
                    when (result) {
                        is DataResourceResult.Success -> {
                            val quizzes =
                                result.data.map { QuizUIModelMapper.mapToLayerModel(it) }.toMutableList()
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    quizzes = quizzes,
                                    currentIndex = 0,
                                    totalCount = quizzes.firstOrNull()?.totalCount ?: 0
                                )
                            }
                            Log.d(TAG, "퀴즈 불러오기 성공: ${quizzes.size}개")
                        }

                        is DataResourceResult.Failure -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    error = result.exception
                                )
                            }
                            Log.d(TAG, "퀴즈 불러오기 실패: ${result.exception}")
                        }

                        DataResourceResult.Loading -> {}
                        DataResourceResult.DummyConstructor -> {}
                    }
                }
        }
    }

    fun loadWrongAnswerQuizzes(stageNumber: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            getWrongAnswerQuizzesUseCase(GetWrongAnswerQuizzesUseCase.Params(stageNumber))
                .collect { result ->
                    when (result) {
                        is DataResourceResult.Success -> {
                            val quizzes = result.data.map { QuizUIModelMapper.mapToLayerModel(it) }.toMutableList()
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    quizzes = quizzes,
                                    currentIndex = 0,
                                    totalCount = quizzes.firstOrNull()?.totalCount ?: quizzes.size
                                )
                            }
                            Log.d(TAG, "오답 퀴즈 불러오기 성공: ${quizzes.size}개")
                        }

                        is DataResourceResult.Failure -> {
                            _uiState.update {
                                it.copy(isLoading = false, error = result.exception)
                            }
                            Log.d(TAG, "오답 퀴즈 불러오기 실패: ${result.exception}")
                        }

                        DataResourceResult.Loading -> {}
                        DataResourceResult.DummyConstructor -> {}
                    }
                }
        }
    }

    fun checkAnswer(selectedAnswer: String) {
        val currentQuiz = uiState.value.currentQuiz ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(error = null) }

            val result = getCheckAnswerUseCase(
                GetCheckAnswerUseCase.Params(
                    stage = currentQuiz.stage,
                    number = currentQuiz.number,
                    answer = selectedAnswer
                )
            )
            when (result) {
                is DataResourceResult.Success -> {
                    val resultType = if (result.data) QuizResultType.CORRECT else QuizResultType.WRONG
                    _uiState.update {
                        it.copy(
                            quizResultType = resultType,
                        )
                    }
                    Log.d(TAG, "정답 여부: ${result.data}")
                }

                is DataResourceResult.Failure -> {
                    _uiState.update {
                        it.copy(
                            quizResultType = QuizResultType.DEFAULT,
                            error = result.exception
                        )
                    }
                    Log.d(TAG, "정답 체크 실패: ${result.exception}")
                }

                DataResourceResult.Loading -> {}
                DataResourceResult.DummyConstructor -> {}
            }
        }

    }

    fun checkClearStage(onResult: (QuizClearType) -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(error = null) }

            when (val result = getCheckStageUseCase(
                GetCheckStageUseCase.Params(uiState.value.currentQuiz?.stage ?: 0)
            )) {
                is DataResourceResult.Success -> {
                    val clearedModel = QuizClearedUIModelMapper.mapToLayerModel(result.data)
                    val clearType = when {
                        clearedModel.message == "All Clear" && clearedModel.isCleared -> QuizClearType.ALL_CLEAR
                        clearedModel.message == "First" && clearedModel.isCleared -> QuizClearType.CLEAR_SUCCESS
                        clearedModel.message == "First" && !clearedModel.isCleared -> QuizClearType.CLEAR_FAILED
                        clearedModel.message == "오답 노트" && clearedModel.isCleared -> QuizClearType.WRONG_ANSWER_QUIZ_CLEAR
                        clearedModel.message == "오답 노트" && !clearedModel.isCleared -> QuizClearType.WRONG_ANSWER_QUIZ_FAILED
                        clearedModel.message == "복습" && clearedModel.isCleared -> QuizClearType.REVIEW_COMPLETED
                        else -> QuizClearType.CLEAR_FAILED
                    }
                    onResult(clearType)
                }

                is DataResourceResult.Failure -> {
                    _uiState.update { it.copy(error = result.exception) }
                }

                else -> Unit
            }

        }
    }

    fun moveToNext() {
        _uiState.update {
            if (it.currentIndex < it.quizzes.lastIndex) {
                it.copy(
                    currentIndex = it.currentIndex + 1,
                    quizResultType = QuizResultType.DEFAULT
                )
            } else it
        }
    }

    fun isLastQuiz(): Boolean {
        return uiState.value.currentIndex == uiState.value.quizzes.lastIndex
    }

    fun clearError() {
        _uiState.update {
            it.copy(error = null)
        }
    }

    companion object {
        private const val TAG = "QuizViewModel"
    }
}

data class QuizUIState(
    override val isLoading: Boolean = false,
    override val error: PayKidsException? = null,
    val quizzes: MutableList<QuizUIModel> = mutableListOf(),
    val totalCount: Int = 0,
    val currentIndex: Int = 0,
    val quizResultType: QuizResultType = QuizResultType.DEFAULT,
) : UIState() {
    val currentQuiz: QuizUIModel?
        get() = quizzes.getOrNull(currentIndex)
}