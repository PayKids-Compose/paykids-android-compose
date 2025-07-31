package com.paykidscompose.presentation.screens.allowance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paykidscompose.common.enums.AllowanceType
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.allowance.expense.GetExpenseDayUseCase
import com.paykidscompose.common.usecase.allowance.expense.GetExpenseMonthDailyAmountUseCase
import com.paykidscompose.common.usecase.allowance.expense.GetExpenseMonthMostCategoryUseCase
import com.paykidscompose.common.usecase.allowance.expense.GetExpenseMonthTotalAmountUseCase
import com.paykidscompose.common.usecase.allowance.expense.ReplaceExpenseUseCase
import com.paykidscompose.common.usecase.allowance.expense.SaveExpenseUseCase
import com.paykidscompose.common.usecase.allowance.income.GetIncomeDayUseCase
import com.paykidscompose.common.usecase.allowance.income.GetIncomeMonthDailyAmountUseCase
import com.paykidscompose.common.usecase.allowance.income.ReplaceIncomeUseCase
import com.paykidscompose.common.usecase.allowance.income.SaveIncomeUseCase
import com.paykidscompose.common.util.today
import com.paykidscompose.presentation.base.UIState
import com.paykidscompose.presentation.mapper.allowance.AllowanceChartAmountUIModelMapper
import com.paykidscompose.presentation.mapper.allowance.AllowanceChartCategoryUIModelMapper
import com.paykidscompose.presentation.mapper.allowance.AllowanceChartUIModelMapper
import com.paykidscompose.presentation.model.allowance.AllowanceDiaryUIModel
import com.paykidscompose.presentation.util.formatAmount
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class AllowanceDiaryViewModel(
    private val getExpenseMonthTotalAmountUseCase: GetExpenseMonthTotalAmountUseCase,
    private val getExpenseMonthMostCategoryUseCase: GetExpenseMonthMostCategoryUseCase,
    private val getExpenseMonthDailyAmountUseCase: GetExpenseMonthDailyAmountUseCase,
    private val getIncomeMonthDailyAmountUseCase: GetIncomeMonthDailyAmountUseCase,
    private val getExpenseDayUseCase: GetExpenseDayUseCase,
    private val getIncomeDayUseCase: GetIncomeDayUseCase,
    private val saveExpenseUseCase: SaveExpenseUseCase,
    private val saveIncomeUseCase: SaveIncomeUseCase,
    private val replaceExpenseUseCase: ReplaceExpenseUseCase,
    private val replaceIncomeUseCase: ReplaceIncomeUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AllowanceDiaryUIState(uiModel = AllowanceDiaryUIModel()))
    val uiState = _uiState.asStateFlow()

    fun onPrevMonth() {
        _uiState.update {
            val newMonth = it.currentMonth.minusMonths(1)
            it.copy(currentMonth = newMonth)
        }
        load()
    }

    fun onNextMonth() {
        _uiState.update {
            val newMonth = it.currentMonth.plusMonths(1)
            it.copy(currentMonth = newMonth)
        }
        load()
    }

    fun onSelectedDate(date: LocalDate) {
        _uiState.update { it.copy(selectedDate = date) }
        getSelectedDayTransactions()
    }

    fun onAllowanceType(type: AllowanceType) {

    }

    fun onReplaceDialog() {
        _uiState.update {
            it.copy(
                showReplaceDialog = true
            )
        }
    }

    fun onDismissReplaceDialog() {
        _uiState.update {
            it.copy(
                showReplaceDialog = false
            )
        }
    }

    fun onAddDialog() {
        _uiState.update {
            it.copy(
                showAddDialog = true
            )
        }
    }

    fun onDismissAddDialog() {
        _uiState.update {
            it.copy(
                showAddDialog = false
            )
        }
    }

    fun load() {
        viewModelScope.launch {
            getMonthTotalAmountExpense()
            getMonthMostCategoryExpense()
            getMonthDailyAmounts()
        }
    }


    private suspend fun getMonthTotalAmountExpense() {
        _uiState.update {
            it.copy(isLoading = true)
        }

        val result = getExpenseMonthTotalAmountUseCase(
            GetExpenseMonthTotalAmountUseCase.Params(
                _uiState.value.currentMonth.year,
                _uiState.value.currentMonth.monthValue
            )
        )
        when (result) {
            is DataResourceResult.Success -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        uiModel = it.uiModel.copy(
                            headerTotalExpense = formatAmount(result.data)
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

            DataResourceResult.Loading, DataResourceResult.DummyConstructor -> {}
        }

    }

    private suspend fun getMonthMostCategoryExpense() {
        _uiState.update {
            it.copy(isLoading = true)
        }

        val result = getExpenseMonthMostCategoryUseCase(
            GetExpenseMonthMostCategoryUseCase.Params(
                _uiState.value.currentMonth.year,
                _uiState.value.currentMonth.monthValue
            )
        ).first()

        when (result) {
            is DataResourceResult.Success -> {
                val mostCategoryExpense = result.data.first()
                val layerModel = AllowanceChartCategoryUIModelMapper.mapToLayerModel(mostCategoryExpense)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        uiModel = it.uiModel.copy(
                            mostCategoryExpense = layerModel
                        )
                    )
                }
            }

            is DataResourceResult.Failure -> {
                _uiState.update {
                    it.copy(isLoading = false, error = result.exception)
                }
            }

            DataResourceResult.DummyConstructor, DataResourceResult.Loading -> {
                _uiState.update {
                    it.copy(isLoading = true)
                }
            }
        }

    }

    private suspend fun getMonthDailyAmounts() {
        _uiState.update {
            it.copy(isLoading = true)
        }

        val year = _uiState.value.currentMonth.year
        val month = _uiState.value.currentMonth.monthValue

        combine(
            getExpenseMonthDailyAmountUseCase(
                GetExpenseMonthDailyAmountUseCase.Params(year, month)
            ),
            getIncomeMonthDailyAmountUseCase(
                GetIncomeMonthDailyAmountUseCase.Params(year, month)
            )
        ) { expenseResult, incomeResult ->
            Pair(expenseResult, incomeResult)
        }.collect { (expenseResult, incomeResult) ->
            when {
                expenseResult is DataResourceResult.Success && incomeResult is DataResourceResult.Success -> {
                    val expenseList =
                        expenseResult.data.map { AllowanceChartAmountUIModelMapper.mapToLayerModel(it) }
                    val incomeList =
                        incomeResult.data.map { AllowanceChartAmountUIModelMapper.mapToLayerModel(it) }

                    val expenseMap = expenseList.groupBy { it.date }
                        .mapValues { (_, chartAmountUIModel) ->
                            chartAmountUIModel.sumOf { it.amount }
                        }

                    val incomeMap = incomeList.groupBy { it.date }
                        .mapValues { (_, chartAmountUIModel) ->
                            chartAmountUIModel.sumOf { it.amount }
                        }

                    val allDates = (expenseMap.keys + incomeMap.keys).toSet()

                    val resultMap = allDates.associateWith { date ->
                        Pair(expenseMap[date] ?: 0, incomeMap[date] ?: 0)
                    }

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            uiModel = it.uiModel.copy(
                                monthlyDailyAmounts = resultMap
                            )
                        )
                    }
                }

                expenseResult is DataResourceResult.Failure -> {
                    _uiState.update {
                        it.copy(isLoading = false, error = expenseResult.exception)
                    }
                }

                incomeResult is DataResourceResult.Failure -> {
                    _uiState.update {
                        it.copy(isLoading = false, error = incomeResult.exception)
                    }
                }
            }
        }
    }

    private fun getSelectedDayTransactions() {
        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
            combine(
                getExpenseDayUseCase(
                    GetExpenseDayUseCase.Params(_uiState.value.selectedDate)
                ),
                getIncomeDayUseCase(
                    GetIncomeDayUseCase.Params(_uiState.value.selectedDate)
                )
            ) { expenseResult, incomeResult ->
                if (expenseResult is DataResourceResult.Success && incomeResult is DataResourceResult.Success) {
                    val combineList = expenseResult.data + incomeResult.data
                    combineList.map { AllowanceChartUIModelMapper.mapToLayerModel(it) }
                        .sortedByDescending { it.date }
                } else {
                    emptyList()
                }
            }.collect { list ->
                _uiState.update {
                    it.copy(
                        uiModel = it.uiModel.copy(
                            selectedDayTransactions = list
                        )
                    )
                }
            }
        }

        _uiState.update {
            it.copy(
                isLoading = false
            )
        }

    }

    fun clearError() {
        _uiState.update {
            it.copy(error = null)
        }
    }
}


data class AllowanceDiaryUIState(
    override val isLoading: Boolean = false,
    override val error: PayKidsException? = null,
    val currentMonth: LocalDate = today.withDayOfMonth(1),
    val selectedDate: LocalDate = today,
    val uiModel: AllowanceDiaryUIModel,
    val showReplaceDialog: Boolean = false,
    val showAddDialog: Boolean = false
) : UIState()