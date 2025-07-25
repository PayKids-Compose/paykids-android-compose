package com.paykidscompose.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.authentication.LoginUseCase
import com.paykidscompose.presentation.base.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState = _uiState.asStateFlow()

//    init {
//        Log.e(TAG, "뷰모델 생성!!!!!!!")
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        Log.e(TAG, "뷰모델 소멸!!!!!!")
//    }

    fun kakaoLogin() {
        if (_uiState.value.isLoading || _uiState.value.isLoginSuccess) return

        _uiState.update {
            it.copy(isLoading = true, error = null)
        }

        viewModelScope.launch {
            when (val result = loginUseCase()) {
                is DataResourceResult.Success -> {
                    _uiState.update {
                        it.copy(isLoading = false, isLoginSuccess = true)
                    }
                }

                is DataResourceResult.Failure -> {
                    _uiState.update {
                        // 에러는 이렇게 사용 하면 안되고 리포지토리랑 usecase 에서 throwable를 던지는 것을 SnackBarException으로 던지고 메시지도 사용자에게 보여줄 수 있는 메시지로 띄우기.
                        // it.copy(isLoading = false, error = PayKidsException.SnackBarException(message = result.exception.message))
                        it.copy(isLoading = false, error = PayKidsException.SnackBarException(message = ""))
                    }
                }

                DataResourceResult.DummyConstructor, DataResourceResult.Loading -> {}
            }
        }

    }

    companion object {
        private const val TAG = "LoginViewModel"
    }

}

data class LoginUIState(
    override val isLoading: Boolean = false,
    override val error: PayKidsException? = null,
    val isRegistered: Boolean = false,
    val isLoginSuccess: Boolean = false
) : UIState()