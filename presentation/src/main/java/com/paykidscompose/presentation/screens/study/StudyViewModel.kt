package com.paykidscompose.presentation.screens.study

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.study.GetChatResponseUseCase
import com.paykidscompose.common.usecase.user.GetUserUseCase
import com.paykidscompose.presentation.base.UIState
import com.paykidscompose.presentation.mapper.my.MyInfoUIModelMapper
import com.paykidscompose.presentation.model.study.ChatMessageUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StudyViewModel(
    private val getChatResponseUseCase: GetChatResponseUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(StudyUIState())
    val uiState = _uiState.asStateFlow()

    init {
        loadNickname()
    }

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

    fun loadNickname() {
        if (_uiState.value.isLoading) return

        _uiState.update {
            it.copy(isLoading = true, error = null)
        }

        viewModelScope.launch {
            getUserUseCase().collectLatest { result ->
                when (result) {
                    is DataResourceResult.Success -> {
                        val uiModel = MyInfoUIModelMapper.mapToLayerModel(result.data)
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                userNickname = uiModel.nickname,
                                error = null
                            )
                        }
                    }

                    is DataResourceResult.Failure -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = result.exception
                            )
                        }
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
    val userInput: String = "",
    val userNickname: String = ""
) : UIState()