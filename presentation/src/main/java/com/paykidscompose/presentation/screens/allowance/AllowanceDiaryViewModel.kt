package com.paykidscompose.presentation.screens.allowance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paykidscompose.common.enums.AllowanceType
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.presentation.base.UIState
import com.paykidscompose.presentation.dummy.DummyDataManager
import com.paykidscompose.presentation.model.AllowanceDiaryUIModel
import com.paykidscompose.presentation.model.toUIModel
import com.paykidscompose.presentation.util.DateFormatterDay
import com.paykidscompose.presentation.util.DateFormatterMonth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class AllowanceDiaryViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AllowanceDiaryUIState())
    val uiState = _uiState.asStateFlow()

    private val dummyDataManager = DummyDataManager()
    private val allAllowanceCharts = dummyDataManager.getAllAllowanceCharts()

    init {
        calculateAll()
    }

    fun onPrevMonth() {
        _uiState.update {
            val newMonth = it.currentMonth.minusMonths(1)
            it.copy(currentMonth = newMonth)
        }
        calculateAll()
    }

    fun onNextMonth() {
        _uiState.update {
            val newMonth = it.currentMonth.plusMonths(1)
            it.copy(currentMonth = newMonth)
        }
        calculateAll()
    }

    fun onDateSelected(date: LocalDate) {
        _uiState.update { it.copy(selectedDate = date) }
        updateSelectedUIModels()
    }

    fun setShowDialog(value: Boolean) {
        _uiState.update { it.copy(showInputDialog = value) }
    }

    fun calculateAll() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            runCatching {
                updateSelectedUIModels()
                updateTotalExpense()
                updateDailySummary()
                updateMaxExpenseCategory()
            }.onFailure { throwable ->
                _uiState.update {
                    it.copy(
                        error = PayKidsException.SnackBarException(
                            message = throwable.message ?: ""
                        )
                    )
                }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun updateSelectedUIModels() {
        val selectedDateStr = _uiState.value.selectedDate.format(DateFormatterDay)
        val uiModels = allAllowanceCharts
            .filter { it.date == selectedDateStr }
            .map { it.toUIModel() }

        _uiState.update { it.copy(selectedUIModels = uiModels) }
    }

    private fun updateTotalExpense() {
        val monthPrefix = _uiState.value.currentMonth.format(DateFormatterMonth)
        val total = allAllowanceCharts
            .filter {
                it.allowanceType == AllowanceType.EXPENSE && it.date.startsWith(monthPrefix)
            }
            .sumOf { it.amount }

        _uiState.update { it.copy(totalExpense = total) }
    }

    private fun updateDailySummary() {
        val summary = allAllowanceCharts
            .groupBy { LocalDate.parse(it.date) }
            .mapValues { (_, list) ->
                val income = list.filter { it.allowanceType == AllowanceType.INCOME }
                    .sumOf { it.amount }
                val expense = list.filter { it.allowanceType == AllowanceType.EXPENSE }
                    .sumOf { it.amount }
                income to expense
            }

        _uiState.update { it.copy(dailySummary = summary) }
    }

    private fun updateMaxExpenseCategory() {
        viewModelScope.launch {
            val monthPrefix = _uiState.value.currentMonth.format(DateFormatterMonth)
            val expenseByCategory = allAllowanceCharts
                .filter {
                    it.allowanceType == AllowanceType.EXPENSE && it.date.startsWith(monthPrefix)
                }
                .groupBy { it.category }
                .mapValues { entry -> entry.value.sumOf { it.amount } }

            val max = expenseByCategory.maxByOrNull { it.value }
            _uiState.update { it.copy(maxExpenseCategoryAndAmount = max?.toPair()) }
        }
    }
}


data class AllowanceDiaryUIState(
    override val isLoading: Boolean = false,
    override val error: PayKidsException? = null,
    val currentMonth: LocalDate = LocalDate.now().withDayOfMonth(1),
    val selectedDate: LocalDate = LocalDate.now(),
    val selectedUIModels: List<AllowanceDiaryUIModel> = emptyList(),
    val totalExpense: Int = 0,
    val dailySummary: Map<LocalDate, Pair<Int, Int>> = emptyMap(),
    val maxExpenseCategoryAndAmount: Pair<String, Int>? = null,
    val showInputDialog: Boolean = false
) : UIState()