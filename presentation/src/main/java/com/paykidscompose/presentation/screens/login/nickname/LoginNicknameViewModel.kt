package com.paykidscompose.presentation.screens.login.nickname

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.user.SaveNicknameUseCase
import com.paykidscompose.presentation.base.UIEvent
import com.paykidscompose.presentation.base.UIState
import com.paykidscompose.presentation.model.LoginNicknameUIModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginNicknameViewModel(
    private val saveNicknameUseCase: SaveNicknameUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginNicknameUIState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

//    init {
//        Log.e(TAG, "닉네임 뷰모델 생성!!!!!!!")
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        Log.e(TAG, "닉네임 뷰모델 소멸!!!!!!")
//    }

    fun updateNicknameInput(nickname: String) {
        _uiState.update {
            it.copy(uiModel = it.uiModel?.copy(nickname))
        }
    }

    fun saveNickname() {
        if (_uiState.value.isLoading) return
        val nickname = _uiState.value.uiModel?.nickname ?: ""

        _uiState.update {
            it.copy(isLoading = true, error = null)
        }

        viewModelScope.launch {
            when (val result = saveNicknameUseCase(SaveNicknameUseCase.Params(nickname))) {
                is DataResourceResult.Success -> {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    _uiEvent.emit(UIEvent.SuccessShowToast("닉네임 저장 완료! 회원가입 축하합니다!"))
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

    fun clearError() {
        _uiState.update {
            it.copy(error = null)
        }
    }

    companion object {
        private const val TAG = "LoginNicknameViewModel"
    }
}

data class LoginNicknameUIState(
    override val isLoading: Boolean = false,
    override val error: PayKidsException? = null,
    val uiModel: LoginNicknameUIModel? = null
) : UIState()