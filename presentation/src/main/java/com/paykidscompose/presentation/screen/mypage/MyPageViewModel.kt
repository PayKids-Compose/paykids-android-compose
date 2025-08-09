package com.paykidscompose.presentation.screen.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.authentication.LogoutUseCase
import com.paykidscompose.common.usecase.user.GetUserUseCase
import com.paykidscompose.presentation.base.UIEvent
import com.paykidscompose.presentation.base.UIState
import com.paykidscompose.presentation.mapper.my.MyPageUIModelMapper
import com.paykidscompose.presentation.model.MyPageUIModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyPageViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MyPageUIState())
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
                        val uiModel = MyPageUIModelMapper.mapToLayerModel(result.data)
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

    fun onLogoutClick() {
        _uiState.update { it.copy(showLogoutDialog = true) }
    }

    fun dismissLogoutDialog() {
        _uiState.update { it.copy(showLogoutDialog = false) }
    }

    fun confirmLogout() {
        viewModelScope.launch {
            when (val result = logoutUseCase()) {
                is DataResourceResult.Success -> {
                    _uiState.update {
                        it.copy(uiModel = null, showLogoutDialog = false)
                    }
                    _uiEvent.emit(UIEvent.SuccessShowToast("로그아웃 되었습니다!"))
                }

                is DataResourceResult.Failure -> {
                    _uiState.update {
                        it.copy(
                            error = result.exception,
                            showLogoutDialog = false
                        )
                    }
                }

                DataResourceResult.Loading, DataResourceResult.DummyConstructor -> {}
            }
        }
    }

    fun clearError() {
        _uiState.update {
            it.copy(error = null)
        }
    }
}

data class MyPageUIState(
    override val isLoading: Boolean = false,
    override val error: PayKidsException? = null,
    val uiModel: MyPageUIModel? = null,
    val showLogoutDialog: Boolean = false
) : UIState()