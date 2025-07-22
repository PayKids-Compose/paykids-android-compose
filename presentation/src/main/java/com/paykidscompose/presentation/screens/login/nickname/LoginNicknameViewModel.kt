package com.paykidscompose.presentation.screens.login.nickname

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.user.SaveNicknameUseCase
import com.paykidscompose.presentation.base.UIState
import com.paykidscompose.presentation.model.LoginNicknameUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginNicknameViewModel(
    private val saveNicknameUseCase: SaveNicknameUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginNicknameUIState())
    val uiState = _uiState.asStateFlow()

    fun updateNicknameInput(nickname: String) {
        _uiState.update {
            it.copy(uiModel = it.uiModel.copy(nickname))
        }
    }

    fun saveNickname() {
        if (_uiState.value.isLoading) return
        val nickname = _uiState.value.uiModel.nickname

        _uiState.update {
            it.copy(isLoading = true, error = null)
        }

        viewModelScope.launch {
            when (val result = saveNicknameUseCase(SaveNicknameUseCase.Params(nickname))) {
                is DataResourceResult.Success -> {
                    Log.d("LoginNicknameViewModel", "Success")
                    _uiState.update {
                        it.copy(isLoading = false, isSaveSuccess = true)
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

}

data class LoginNicknameUIState(
    override val isLoading: Boolean = false,
    override val error: PayKidsException? = null,
    val uiModel: LoginNicknameUIModel = LoginNicknameUIModel(""),
    val isSaveSuccess: Boolean = false
) : UIState()