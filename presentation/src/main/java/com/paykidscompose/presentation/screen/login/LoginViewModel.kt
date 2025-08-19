package com.paykidscompose.presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.authentication.LoginUseCase
import com.paykidscompose.presentation.base.UIEvent
import com.paykidscompose.presentation.base.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

//    init {
//        Log.e(TAG, "뷰모델 생성!!!!!!!")
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        Log.e(TAG, "뷰모델 소멸!!!!!!")
//    }

    fun kakaoLogin() {
        if (_uiState.value.isLoading) return

        _uiState.update {
            it.copy(isLoading = true, error = null)
        }

        viewModelScope.launch {
            when (val result = loginUseCase()) {
                is DataResourceResult.Success -> {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    _uiEvent.emit(UIEvent.SuccessShowToast("카카오 로그인 성공!!"))
                }

                is DataResourceResult.Failure -> {
                    _uiState.update {
                        it.copy(isLoading = false, error = result.exception)
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
        private const val TAG = "LoginViewModel"
    }

}

data class LoginUIState(
    override val isLoading: Boolean = false,
    override val error: PayKidsException? = null
) : UIState()