package com.paykidscompose.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun kakaoLogin() {
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
                        it.copy(isLoading = false, error = result.exception.message)
                    }
                }
                DataResourceResult.DummyConstructor -> TODO()
                DataResourceResult.Loading -> TODO()
            }
        }

    }

}

data class LoginUIState(
    override val isLoading: Boolean = false,
    override val error: String? = null,
    val isLoginSuccess: Boolean = false
) : UIState()