package com.paykidscompose.presentation.screens.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paykidscompose.presentation.dummy.DummyUser
import com.paykidscompose.presentation.model.MyPageUIModel
import com.paykidscompose.presentation.model.MyPageUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyPageViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(MyPageUIState())
    val uiState = _uiState.asStateFlow()

    fun load() {
        if(_uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, error = null)
            }

            delay(1000)

            runCatching {
                val user = withContext(Dispatchers.IO) {
                    DummyUser.getUser()
                }
                user
            }.onSuccess { user ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        myPage = MyPageUIModel(
                            nickname = user.nickname,
                            image = user.profileImageURL
                        ),
                        error = null
                    )
                }
            }.onFailure { throwable ->
                _uiState.update { it.copy(
                    isLoading = false,
                    error = throwable.message,
                    myPage = null
                )}
            }
        }
    }

    fun onLogoutClick(){
        _uiState.update { it.copy(showLogoutDialog = true)}
    }

    fun dismissLogoutDialog() {
        _uiState.update { it.copy(showLogoutDialog = false) }
    }

    fun confirmLogout() {
        _uiState.update { it.copy(showLogoutDialog = false)}
    }

}