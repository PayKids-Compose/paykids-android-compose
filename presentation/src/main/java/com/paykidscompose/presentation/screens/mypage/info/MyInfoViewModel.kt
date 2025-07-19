package com.paykidscompose.presentation.screens.mypage.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.user.DeleteUserUseCase
import com.paykidscompose.common.usecase.user.GetUserUseCase
import com.paykidscompose.common.usecase.user.SaveNicknameUseCase
import com.paykidscompose.common.usecase.user.SaveNicknameUseCase.Params
import com.paykidscompose.presentation.base.UIState
import com.paykidscompose.presentation.model.MyInfoUIModel
import com.paykidscompose.presentation.model.MyInfoUIModelMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyInfoViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val saveNicknameUseCase: SaveNicknameUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MyInfoUIState())
    val uiState = _uiState.asStateFlow()

    init {
        load()
    }

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
                                myInfo = uiModel,
                                error = null
                            )
                        }
                    }

                    is DataResourceResult.Failure -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = PayKidsException.SnackBarException(
                                    message = result.exception.message ?: "유저 정보를 불러오지 못했습니다."
                                )
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
                myInfo = it.myInfo?.copy(nickname = nickname)
            )
        }
    }

    fun saveNickname() {
        val nickname = _uiState.value.myInfo?.nickname ?: ""

        viewModelScope.launch {
            when (val result = saveNicknameUseCase(Params(nickname))) {
                is DataResourceResult.Success -> {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                }

                is DataResourceResult.Failure -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = PayKidsException.SnackBarException(
                                message = result.exception.message ?: "닉네임 저장 과정에 오류가 발생했습니다."
                            )
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
                DataResourceResult.DummyConstructor -> {}
                is DataResourceResult.Failure -> {}
                DataResourceResult.Loading -> {}
                is DataResourceResult.Success -> {}
            }
        }

        _uiState.update {
            it.copy(showPopupDialog = false, isDeleteUserSuccess = true)
        }
    }
}

data class MyInfoUIState(
    override val isLoading: Boolean = false,
    override val error: PayKidsException? = null,
    val myInfo: MyInfoUIModel? = null,
    val showPopupDialog: Boolean = false,
    val isDeleteUserSuccess: Boolean = false
) : UIState()