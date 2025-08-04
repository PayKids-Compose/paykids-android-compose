package com.paykidscompose.presentation.screens.study

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.study.GetChatResponseUseCase
import com.paykidscompose.presentation.base.UIState
import com.paykidscompose.presentation.model.study.ChatMessageUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StudyViewModel(
    private val getChatResponseUseCase: GetChatResponseUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(StudyUIState())
    val uiState = _uiState.asStateFlow()

    fun onUserInputChange(input: String) {
        _uiState.update { it.copy(userInput = input) }
    }

    fun sendUserInput() {
        val inputText = _uiState.value.userInput
        if (inputText.isBlank()) return

        val userMessage = ChatMessageUIModel(
            text = inputText,
            isFromGpt = false
        )

        _uiState.update {
            it.copy(
                messages = it.messages + userMessage,
                userInput = "", // 입력창 비움
                isLoading = true, error = null
            )
        }

        viewModelScope.launch {
            getChatResponseUseCase(GetChatResponseUseCase.Params(inputText))
                .collectLatest { result ->
                    when (result) {
                        is DataResourceResult.Success -> {
                            val gptMessage = ChatMessageUIModel(
                                text = result.data.response,
                                isFromGpt = true
                            )
                            _uiState.update {
                                it.copy(
                                    messages = it.messages + gptMessage,
                                    isLoading = false
                                )
                            }
                        }

                        is DataResourceResult.Failure -> {
                            _uiState.update { it.copy(error = result.exception) }
                        }

                        DataResourceResult.Loading -> {}
                        DataResourceResult.DummyConstructor -> {}
                    }
                }
        }
    }

    fun clearError() {
        _uiState.update {
            it.copy(error = null)
        }
    }
}

data class StudyUIState(
    override val isLoading: Boolean = false,
    override val error: PayKidsException? = null,
    val messages: List<ChatMessageUIModel> = listOf(),
    val userInput: String = ""
) : UIState()