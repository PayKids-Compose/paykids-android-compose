package com.paykidscompose.presentation.screens.allowance.analysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paykidscompose.common.enums.AllowanceType
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.allowance.expense.DeleteExpenseCategoryUseCase
import com.paykidscompose.common.usecase.allowance.expense.GetExpenseMonthAllCategoryUseCase
import com.paykidscompose.common.usecase.allowance.expense.GetExpenseMonthTotalAmountUseCase
import com.paykidscompose.common.usecase.allowance.expense.SaveExpenseCategoryUseCase
import com.paykidscompose.common.usecase.allowance.income.DeleteIncomeCategoryUseCase
import com.paykidscompose.common.usecase.allowance.income.GetIncomeMonthAllCategoryUseCase
import com.paykidscompose.common.usecase.allowance.income.GetIncomeMonthTotalAmountUseCase
import com.paykidscompose.common.usecase.allowance.income.SaveIncomeCategoryUseCase
import com.paykidscompose.common.util.today
import com.paykidscompose.presentation.base.UIEvent
import com.paykidscompose.presentation.base.UIState
import com.paykidscompose.presentation.mapper.allowance.AllowanceChartCategoryUIModelMapper
import com.paykidscompose.presentation.model.allowance.TransactionAnalysisUIModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class TransactionAnalysisViewModel(
    private val getExpenseMonthTotalAmountUseCase: GetExpenseMonthTotalAmountUseCase,
    private val getIncomeMonthTotalAmountUseCase: GetIncomeMonthTotalAmountUseCase,
    private val getExpenseMonthAllCategoryUseCase: GetExpenseMonthAllCategoryUseCase,
    private val getIncomeMonthAllCategoryUseCase: GetIncomeMonthAllCategoryUseCase,
    private val deleteExpenseCategoryUseCase: DeleteExpenseCategoryUseCase,
    private val deleteIncomeCategoryUseCase: DeleteIncomeCategoryUseCase,
    private val saveExpenseCategoryUseCase: SaveExpenseCategoryUseCase,
    private val saveIncomeCategoryUseCase: SaveIncomeCategoryUseCase
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(TransactionAnalysisUIState(uiModel = TransactionAnalysisUIModel()))
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun load() {
        viewModelScope.launch {
            when (_uiState.value.selectedType) {
                AllowanceType.INCOME -> {
                    getIncomeMonthTotalAmount()
                    getIncomeMonthAllCategory()
                }

                AllowanceType.EXPENSE -> {
                    getExpenseMonthTotalAmount()
                    getExpenseMonthAllCategory()
                }
            }
        }
    }

    fun onPrevMonth() {
        _uiState.update {
            it.copy(currentMonth = it.currentMonth.minusMonths(1))
        }

        viewModelScope.launch {
            when (_uiState.value.selectedType) {
                AllowanceType.INCOME -> {
                    getIncomeMonthTotalAmount()
                    getIncomeMonthAllCategory()
                }

                AllowanceType.EXPENSE -> {
                    getExpenseMonthTotalAmount()
                    getExpenseMonthAllCategory()
                }
            }
        }
    }

    fun onNextMonth() {
        _uiState.update {
            it.copy(currentMonth = it.currentMonth.plusMonths(1))
        }

        viewModelScope.launch {
            when (_uiState.value.selectedType) {
                AllowanceType.INCOME -> {
                    getIncomeMonthTotalAmount()
                    getIncomeMonthAllCategory()
                }

                AllowanceType.EXPENSE -> {
                    getExpenseMonthTotalAmount()
                    getExpenseMonthAllCategory()
                }
            }
        }
    }

    fun onAllowanceTypeSelected(type: AllowanceType) {
        _uiState.update {
            it.copy(
                selectedType = type
            )
        }

        viewModelScope.launch {
            when (_uiState.value.selectedType) {
                AllowanceType.INCOME -> {
                    getIncomeMonthTotalAmount()
                    getIncomeMonthAllCategory()
                }

                AllowanceType.EXPENSE -> {
                    getExpenseMonthTotalAmount()
                    getExpenseMonthAllCategory()
                }
            }
        }
    }

    fun inputCategory(category: String) {
        _uiState.update {
            it.copy(
                category = category
            )
        }
    }

    fun onAddClick() {
        _uiState.update {
            it.copy(
                isAddCategory = true
            )
        }
    }

    fun cancelAddCategory() {
        _uiState.update {
            it.copy(
                isAddCategory = false,
                category = ""
            )
        }
    }

    fun confirmAddCategory() {
        when (_uiState.value.selectedType) {
            AllowanceType.INCOME -> {
                saveIncomeCategory()
            }
            AllowanceType.EXPENSE -> {
                saveExpenseCategory()
            }
        }
    }

    private fun saveExpenseCategory() {
        if(!_uiState.value.isAddCategory) return

        viewModelScope.launch {
            val result = saveExpenseCategoryUseCase(
                SaveExpenseCategoryUseCase.Params(
                    _uiState.value.category
                )
            )

            when (result) {
                is DataResourceResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isAddCategory = false,
                            category = ""
                        )
                    }

                    _uiEvent.emit(UIEvent.SuccessShowToast("소비 카테고리를 저장했습니다!"))
                }

                is DataResourceResult.Failure -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.exception,
                            category = "",
                            isAddCategory = false
                        )
                    }
                }

                DataResourceResult.DummyConstructor, DataResourceResult.Loading -> {}
            }
        }
    }

    private fun saveIncomeCategory() {
        if(!_uiState.value.isAddCategory) return

        viewModelScope.launch {
            val result = saveIncomeCategoryUseCase(
                SaveIncomeCategoryUseCase.Params(
                    _uiState.value.category
                )
            )

            when (result) {
                is DataResourceResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isAddCategory = false,
                            category = ""
                        )
                    }

                    _uiEvent.emit(UIEvent.SuccessShowToast("수입 카테고리를 저장했습니다!"))
                }

                is DataResourceResult.Failure -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.exception,
                            category = "",
                            isAddCategory = false
                        )
                    }
                }

                DataResourceResult.DummyConstructor, DataResourceResult.Loading -> {}
            }
        }
    }

    private suspend fun getExpenseMonthTotalAmount() {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }

        val result = getExpenseMonthTotalAmountUseCase(
            GetExpenseMonthTotalAmountUseCase.Params(
                _uiState.value.currentMonth.year,
                _uiState.value.currentMonth.monthValue,
            )
        )

        when (result) {
            is DataResourceResult.Success -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        totalAmount = result.data
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

            DataResourceResult.DummyConstructor, DataResourceResult.Loading -> {}
        }
    }

    private suspend fun getIncomeMonthTotalAmount() {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }

        val result = getIncomeMonthTotalAmountUseCase(
            GetIncomeMonthTotalAmountUseCase.Params(
                _uiState.value.currentMonth.year,
                _uiState.value.currentMonth.monthValue,
            )
        )

        when (result) {
            is DataResourceResult.Success -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        totalAmount = result.data
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

            DataResourceResult.DummyConstructor, DataResourceResult.Loading -> {}
        }
    }

    private suspend fun getExpenseMonthAllCategory() {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }

        getExpenseMonthAllCategoryUseCase(
            GetExpenseMonthAllCategoryUseCase.Params(
                _uiState.value.currentMonth.year,
                _uiState.value.currentMonth.monthValue
            )
        ).collectLatest { result ->

            when (result) {
                is DataResourceResult.Success -> {
                    val chartCategoryUIModels =
                        result.data.map { AllowanceChartCategoryUIModelMapper.mapToLayerModel(it) }
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            uiModel = it.uiModel.copy(
                                transactionList = chartCategoryUIModels
                            )
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

                DataResourceResult.Loading -> {
                    _uiState.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }

                DataResourceResult.DummyConstructor -> {}
            }
        }
    }

    private suspend fun getIncomeMonthAllCategory() {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }

        getIncomeMonthAllCategoryUseCase(
            GetIncomeMonthAllCategoryUseCase.Params(
                _uiState.value.currentMonth.year,
                _uiState.value.currentMonth.monthValue
            )
        ).collectLatest { result ->

            when (result) {
                is DataResourceResult.Success -> {
                    val chartCategoryUIModels =
                        result.data.map { AllowanceChartCategoryUIModelMapper.mapToLayerModel(it) }
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            uiModel = it.uiModel.copy(
                                transactionList = chartCategoryUIModels
                            )
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

                DataResourceResult.Loading -> {
                    _uiState.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }

                DataResourceResult.DummyConstructor -> {}
            }
        }
    }

    fun clearError() {
        _uiState.update {
            it.copy(error = null)
        }
    }
}

data class TransactionAnalysisUIState(
    override val isLoading: Boolean = false,
    override val error: PayKidsException? = null,
    val currentMonth: LocalDate = today.withDayOfMonth(1),
    val uiModel: TransactionAnalysisUIModel,
    val totalAmount: Int = 0,
    val isAddCategory: Boolean = false,
    val category: String = "",
    val selectedType: AllowanceType = AllowanceType.EXPENSE
) : UIState()