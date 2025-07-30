package com.paykidscompose.presentation.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.quiz.GetStageCountUseCase
import com.paykidscompose.common.usecase.quiz.GetStageNameUseCase
import com.paykidscompose.common.usecase.quiz.GetStageNameUseCase.Params
import com.paykidscompose.common.usecase.quiz.GetStageToGoUseCase
import com.paykidscompose.presentation.base.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getStageCountUseCase: GetStageCountUseCase,
    private val getStageToGoUseCase: GetStageToGoUseCase,
    private val getStageNameUseCase: GetStageNameUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun loadAllData() {
        viewModelScope.launch {
            loadTotalStageCount()
            loadUnlockedStageNumber()
            _uiState.update {
                it.copy(isLoading = false, error = null)
            }
        }
    }

    private suspend fun loadTotalStageCount() {
        when (val result = getStageCountUseCase()) {
            is DataResourceResult.Success -> {
                _uiState.update {
                    it.copy(totalStageCount = result.data, error = null)
                }
                Log.d(TAG, "전체 스테이지 개수: ${result.data}")
            }

            is DataResourceResult.Failure -> {
                _uiState.update {
                    it.copy(error = result.exception)
                }
                Log.d(TAG, "error: ${result.exception.message}")
            }

            DataResourceResult.Loading -> {}
            DataResourceResult.DummyConstructor -> {}
        }
    }

    private suspend fun loadUnlockedStageNumber() {
        when (val result = getStageToGoUseCase()) {
            is DataResourceResult.Success -> {
                _uiState.update {
                    it.copy(
                        unlockedStageNumber = result.data,
                        selectedStageIndex = result.data - 1,
                        error = null
                    )
                }
                Log.d(TAG, "해금된 스테이지 번호: ${result.data}")
                loadStageTitle(result.data) // 해금된 스테이지 번호로 스테이지 이름 로드
            }

            is DataResourceResult.Failure -> {
                _uiState.update {
                    it.copy(error = result.exception)
                }
                Log.d(TAG, "error: ${result.exception.message}")
            }

            DataResourceResult.Loading -> {}
            DataResourceResult.DummyConstructor -> {}
        }
    }

    private fun loadStageTitle(stageNumber: Int) {
        viewModelScope.launch {
            when (val result = getStageNameUseCase(Params(stageNumber))) {
                is DataResourceResult.Success -> {
                    _uiState.update {
                        it.copy(stageTitle = result.data, error = null)
                    }
                    Log.d(TAG, "스테이지 이름: ${result.data}")
                }

                is DataResourceResult.Failure -> {
                    _uiState.update {
                        it.copy(error = result.exception)
                    }
                    Log.d(TAG, "error: ${result.exception.message}")
                }

                DataResourceResult.Loading -> {}
                DataResourceResult.DummyConstructor -> {}
            }
        }
    }

    fun onStageSelected(index: Int) {
        _uiState.update {
            it.copy(isLoading = true, error = null)
        }
        _uiState.update { it.copy(selectedStageIndex = index) }
        loadStageTitle(index + 1)
        _uiState.update {
            it.copy(isLoading = false, error = null)
        }
    }

    fun clearError() {
        _uiState.update {
            it.copy(error = null)
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}

data class HomeUiState(
    override val isLoading: Boolean = true,
    override val error: PayKidsException? = null,
    val totalStageCount: Int = 0,
    val unlockedStageNumber: Int = 0,
    val stageTitle: String = "",
    val selectedStageIndex: Int = -1
) : UIState()