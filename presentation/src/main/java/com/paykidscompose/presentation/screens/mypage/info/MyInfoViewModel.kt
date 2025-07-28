package com.paykidscompose.presentation.screens.mypage.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.user.DeleteUserUseCase
import com.paykidscompose.common.usecase.user.GetUserUseCase
import com.paykidscompose.common.usecase.user.ReplaceNicknameUseCase
import com.paykidscompose.presentation.base.UIEvent
import com.paykidscompose.presentation.base.UIState
import com.paykidscompose.presentation.model.MyInfoUIModel
import com.paykidscompose.presentation.model.MyInfoUIModelMapper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyInfoViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val replaceNicknameUseCase: ReplaceNicknameUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MyInfoUIState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun load() {
        if (_uiState.value.isLoading) return

        _uiState.update {
            it.copy(isLoading = true, error = null)
        }

        viewModelScope.launch {
            getUserUseCase().collectLatest { result ->
                when (result) {
                    is DataResourceResult.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is DataResourceResult.Success -> {
                        val uiModel = MyInfoUIModelMapper.mapToLayerModel(result.data)
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                uiModel = uiModel,
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

                    DataResourceResult.DummyConstructor -> {}
                }
            }
        }
    }

    fun updateNickname(nickname: String) {
        _uiState.update {
            it.copy(
                uiModel = it.uiModel?.copy(nickname = nickname)
            )
        }
    }

    fun replaceNickname() {
        _uiState.update {
            it.copy(error = null)
        }

        val nickname = _uiState.value.uiModel?.nickname ?: ""

        viewModelScope.launch {
            when (val result = replaceNicknameUseCase(ReplaceNicknameUseCase.Params(nickname))) {
                is DataResourceResult.Success -> {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }

                    _uiEvent.emit(UIEvent.SuccessShowToast("닉네임이 성공적으로 수정되었습니다.."))
                }

                is DataResourceResult.Failure -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.exception
                        )
                    }
                }

                DataResourceResult.DummyConstructor, DataResourceResult.Loading -> {}
            }
        }
    }

    fun togglePopupDialog() {
        _uiState.update {
            it.copy(showPopupDialog = !it.showPopupDialog)
        }
    }

    fun closePopupDialog() {
        _uiState.update {
            it.copy(showPopupDialog = false)
        }
    }

    fun confirmPopupDialog() {
        viewModelScope.launch {
            when (val result = deleteUserUseCase()) {
                is DataResourceResult.Success -> {
                    _uiState.update {
                        it.copy(uiModel = null, showPopupDialog = false)
                    }

                    _uiEvent.emit(UIEvent.SuccessShowToast("회원탈퇴가 정상적으로 되었습니다."))
                }

                is DataResourceResult.Failure -> {
                    _uiState.update {
                        it.copy(error = result.exception, showPopupDialog = false)
                    }
                }

                DataResourceResult.DummyConstructor, DataResourceResult.Loading -> {}
            }
        }
    }

    fun clearError() {
        _uiState.update {
            it.copy(error = null)
        }
    }
}

data class MyInfoUIState(
    override val isLoading: Boolean = false,
    override val error: PayKidsException? = null,
    val uiModel: MyInfoUIModel? = null,
    val showPopupDialog: Boolean = false
) : UIState()