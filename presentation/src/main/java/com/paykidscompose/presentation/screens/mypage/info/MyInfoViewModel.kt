package com.paykidscompose.presentation.screens.mypage.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paykidscompose.presentation.dummy.DummyUser
import com.paykidscompose.presentation.model.MyInfoUIModel
import com.paykidscompose.presentation.model.MyInfoUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyInfoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MyInfoUIState())
    val uiState = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, error = null)
            }

            runCatching {
                val user = withContext(Dispatchers.IO) {
                    DummyUser.getUser()
                }
                user
            }.onSuccess { user ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        myInfo = MyInfoUIModel(
                            nickname = user.nickname,
                            image = user.profileImageURL,
                            email = user.email
                        ),
                        error = null
                    )
                }
            }.onFailure { throwable ->
                handleError(throwable)
            }
        }
    }

    fun updateNickname(value: String) {
        _uiState.update {
            it.copy(
                myInfo = it.myInfo?.copy(nickname = value)
            )
        }
    }

    fun updateEmail(value: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(myInfo = it.myInfo?.copy(email = value))
            }

            runCatching {
                withContext(Dispatchers.IO) {
                    val user = DummyUser.getUser()
                    DummyUser.setUser(
                        user.copy(
                            email = _uiState.value.myInfo?.email ?: user.email
                        )
                    )
                }
            }.onFailure { throwable ->
                handleError(throwable)
            }
        }
    }

    fun confirmNicknameChange() {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    val user = DummyUser.getUser()
                    DummyUser.setUser(
                        user.copy(
                            nickname = _uiState.value.myInfo?.nickname ?: user.nickname
                        )
                    )
                }
            }.onFailure { throwable ->
                handleError(throwable)
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
        _uiState.update {
            it.copy(showPopupDialog = false)
        }
    }

    private fun handleError(throwable: Throwable) {
        _uiState.update {
            it.copy(
                isLoading = false,
                error = throwable.message,
                myInfo = null
            )
        }
    }
}