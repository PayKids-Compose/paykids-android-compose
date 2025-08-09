package com.paykidscompose.presentation.screen.quiz.entry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.model.quiz.WrongAnswerNoteStatus
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.quiz.GetWrongAnswerStatusUseCase
import com.paykidscompose.presentation.base.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuizEntryViewModel(
    private val getWrongAnswerStatusUseCase: GetWrongAnswerStatusUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(QuizEntryUIState())
    val uiState = _uiState.asStateFlow()

    fun checkWrongAnswerStatus(
        stage: Int,
        onNavigateToWrongNote: () -> Unit,
        onShowDialog: (WrongAnswerNoteStatus) -> Unit
    ) {
        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            getWrongAnswerStatusUseCase(GetWrongAnswerStatusUseCase.Params(stage))
                .collect { result ->
                    _uiState.value = _uiState.value.copy(isLoading = false)

                    when (result) {
                        is DataResourceResult.Success -> {
                            val status = result.data
                            _uiState.value = _uiState.value.copy(wrongAnswerNoteStatus = status)

                            when (status) {
                                is WrongAnswerNoteStatus.AllCorrect -> onShowDialog(status)
                                is WrongAnswerNoteStatus.HasWrongAnswerNote -> onNavigateToWrongNote()
                                is WrongAnswerNoteStatus.NoAttempt -> onShowDialog(status)
                            }
                        }

                        is DataResourceResult.Failure -> {
                            _uiState.value = _uiState.value.copy(error = result.exception)
                        }

                        DataResourceResult.Loading -> {}
                        DataResourceResult.DummyConstructor -> {}
                    }
                }
        }
    }
}

data class QuizEntryUIState(
    override val isLoading: Boolean = false,
    override val error: PayKidsException? = null,
    val wrongAnswerNoteStatus: WrongAnswerNoteStatus? = null
) : UIState()