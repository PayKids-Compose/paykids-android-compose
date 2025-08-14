package com.paykidscompose.presentation.screen.study

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.study.GetChatResponseUseCase
import com.paykidscompose.common.usecase.user.GetUserUseCase
import com.paykidscompose.presentation.base.UIState
import com.paykidscompose.presentation.mapper.my.MyInfoUIModelMapper
import com.paykidscompose.presentation.mapper.study.ChatMessageUIModelMapper
import com.paykidscompose.presentation.model.study.ChatMessageUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

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
            id = UUID.randomUUID().toString(),
            text = inputText,
            isFromGpt = false
        )

        val gptMessageId = UUID.randomUUID().toString()
        _uiState.update {
            it.copy(
                messages = it.messages + userMessage + ChatMessageUIModel(
                    id = gptMessageId,
                    text = "",
                    isFromGpt = true
                ), // 타이핑 애니메이션 위해 GPT 버블 미리 생성,
                userInput = "", // 입력창 비움
                isLoading = true, error = null
            )
        }

        viewModelScope.launch {
            getChatResponseUseCase(GetChatResponseUseCase.Params(inputText))
                .collectLatest { result ->
                    when (result) {
                        is DataResourceResult.Success -> {
                            val uiModel = ChatMessageUIModelMapper.mapToLayerModel(result.data)
                            _uiState.update {
                                it.copy(
                                    messages = it.messages.map { message ->
                                        if (message.id == gptMessageId) {
                                            message.copy(text = uiModel.text)
                                        } else message
                                    },
                                    isLoading = false
                                )
                            }
                        }

                        is DataResourceResult.Failure -> {
                            _uiState.update {
                                it.copy(
                                    messages = it.messages.filter { it.id != gptMessageId }, // 타임아웃 등 응답 실패 시 해당 GPT 버블 삭제
                                    error = result.exception,
                                    isLoading = false
                                )
                            }
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