package com.paykidscompose.presentation.screen.allowance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paykidscompose.common.enums.AllowanceType
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.allowance.GetMonthDailyAmountsUseCase
import com.paykidscompose.common.usecase.allowance.GetSelectDayTransactionsUseCase
import com.paykidscompose.common.usecase.allowance.expense.GetExpenseCategoryListUseCase
import com.paykidscompose.common.usecase.allowance.expense.GetExpenseMonthMostCategoryUseCase
import com.paykidscompose.common.usecase.allowance.expense.GetExpenseMonthTotalAmountUseCase
import com.paykidscompose.common.usecase.allowance.expense.ReplaceExpenseUseCase
import com.paykidscompose.common.usecase.allowance.expense.SaveExpenseUseCase
import com.paykidscompose.common.usecase.allowance.income.GetIncomeCategoryListUseCase
import com.paykidscompose.common.usecase.allowance.income.ReplaceIncomeUseCase
import com.paykidscompose.common.usecase.allowance.income.SaveIncomeUseCase
import com.paykidscompose.common.util.today
import com.paykidscompose.presentation.base.UIEvent
import com.paykidscompose.presentation.base.UIState
import com.paykidscompose.presentation.mapper.allowance.AllowanceChartCategoryUIModelMapper
import com.paykidscompose.presentation.mapper.allowance.AllowanceChartUIModelMapper
import com.paykidscompose.presentation.mapper.allowance.CategoryUIModelMapper
import com.paykidscompose.presentation.model.allowance.AllowanceChartUIModel
import com.paykidscompose.presentation.model.allowance.AllowanceDiaryUIModel
import com.paykidscompose.presentation.util.formatAmount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AllowanceDiaryViewModel @Inject constructor(
    private val getExpenseMonthTotalAmountUseCase: GetExpenseMonthTotalAmountUseCase,
    private val getExpenseMonthMostCategoryUseCase: GetExpenseMonthMostCategoryUseCase,
    private val getExpenseCategoryListUseCase: GetExpenseCategoryListUseCase,
    private val getIncomeCategoryListUseCase: GetIncomeCategoryListUseCase,
    private val getMonthDailyAmountsUseCase: GetMonthDailyAmountsUseCase,
    private val getSelectDayTransactionsUseCase: GetSelectDayTransactionsUseCase,
    private val saveExpenseUseCase: SaveExpenseUseCase,
    private val saveIncomeUseCase: SaveIncomeUseCase,
    private val replaceExpenseUseCase: ReplaceExpenseUseCase,
    private val replaceIncomeUseCase: ReplaceIncomeUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AllowanceDiaryUIState(uiModel = AllowanceDiaryUIModel()))
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onPrevMonth() {
        _uiState.update {
            val newMonth = it.currentMonth.minusMonths(1)
            it.copy(currentMonth = newMonth)
        }

        viewModelScope.launch {
            load()
        }
    }

    fun onNextMonth() {
        _uiState.update {
            val newMonth = it.currentMonth.plusMonths(1)
            it.copy(currentMonth = newMonth)
        }
        viewModelScope.launch {
            load()
        }
    }

    fun onSelectedDate(date: LocalDate) {
        _uiState.update { it.copy(selectedDate = date) }
        getSelectedDayTransactions()
    }

    fun onReplaceDialog(chartUIModel: AllowanceChartUIModel) {
        _uiState.update {
            it.copy(
                uiModel = it.uiModel.copy(
                    selectedTransaction = chartUIModel
                ),
                showReplaceDialog = true
            )
        }
    }

    fun onConfirmReplaceDialog(chartUIModel: AllowanceChartUIModel) {
        _uiState.update {
            it.copy(
                uiModel = it.uiModel.copy(
                    selectedTransaction = null
                ),
                showReplaceDialog = false
            )
        }

        viewModelScope.launch {
            when (chartUIModel.type) {
                AllowanceType.EXPENSE -> replaceExpense(chartUIModel)
                AllowanceType.INCOME -> replaceIncome(chartUIModel)
            }
            load()
        }
    }

    fun onDismissReplaceDialog() {
        _uiState.update {
            it.copy(
                uiModel = it.uiModel.copy(
                    selectedTransaction = null
                ),
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

    fun onConfirmAddDialog(chartUIModel: AllowanceChartUIModel) {
        _uiState.update {
            it.copy(
                showAddDialog = false
            )
        }

        viewModelScope.launch {
            when (chartUIModel.type) {
                AllowanceType.EXPENSE -> saveExpense(chartUIModel)
                AllowanceType.INCOME -> saveIncome(chartUIModel)
            }
            load()
        }
    }

    fun onDismissAddDialog() {
        _uiState.update {
            it.copy(
                showAddDialog = false
            )
        }
    }

    suspend fun load() {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }
        getMonthTotalAmountExpense()
        getMonthMostCategoryExpense()
        getMonthDailyAmounts()
        _uiState.update {
            it.copy(
                isLoading = false
            )
        }
    }

    private suspend fun saveIncome(chartUIModel: AllowanceChartUIModel) {
        val result = saveIncomeUseCase(
            SaveIncomeUseCase.Params(
                AllowanceChartUIModelMapper.mapToModel(
                    chartUIModel
                )
            )
        )

        when (result) {
            DataResourceResult.DummyConstructor -> {}
            is DataResourceResult.Failure -> {
                _uiState.update {
                    it.copy(
                        error = result.exception
                    )
                }
            }

            DataResourceResult.Loading -> {}
            is DataResourceResult.Success -> {
                _uiEvent.emit(UIEvent.SuccessShowToast("수입 내역이 저장되었습니다!"))
            }
        }

    }

    private suspend fun saveExpense(chartUIModel: AllowanceChartUIModel) {
        val result = saveExpenseUseCase(
            SaveExpenseUseCase.Params(
                AllowanceChartUIModelMapper.mapToModel(
                    chartUIModel
                )
            )
        )

        when (result) {
            DataResourceResult.DummyConstructor -> {}
            is DataResourceResult.Failure -> {
                _uiState.update {
                    it.copy(
                        error = result.exception
                    )
                }
            }

            DataResourceResult.Loading -> {}
            is DataResourceResult.Success -> {
                _uiEvent.emit(UIEvent.SuccessShowToast("소비 내역이 저장되었습니다!"))
            }

        }
    }

    private suspend fun replaceIncome(chartUIModel: AllowanceChartUIModel) {
        val result = replaceIncomeUseCase(
            ReplaceIncomeUseCase.Params(
                AllowanceChartUIModelMapper.mapToModel(
                    chartUIModel
                )
            )
        )

        when (result) {
            is DataResourceResult.Success -> {
                _uiEvent.emit(UIEvent.SuccessShowToast("수입 내역이 수정되었습니다!"))
            }

            is DataResourceResult.Failure -> {
                _uiState.update {
                    it.copy(
                        error = result.exception
                    )
                }
            }

            DataResourceResult.DummyConstructor, DataResourceResult.Loading -> {}
        }

    }

    private suspend fun replaceExpense(chartUIModel: AllowanceChartUIModel) {
        val result = replaceExpenseUseCase(
            ReplaceExpenseUseCase.Params(
                AllowanceChartUIModelMapper.mapToModel(
                    chartUIModel
                )
            )
        )

        when (result) {
            is DataResourceResult.Success -> {
                _uiEvent.emit(UIEvent.SuccessShowToast("소비 내역이 수정되었습니다!"))
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

    private suspend fun getMonthTotalAmountExpense() {
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
                        uiModel = it.uiModel.copy(
                            headerTotalExpense = formatAmount(result.data)
                        )
                    )
                }
            }

            is DataResourceResult.Failure -> {
                _uiState.update {
                    it.copy(
                        error = result.exception
                    )
                }
            }

            DataResourceResult.Loading, DataResourceResult.DummyConstructor -> {}
        }

    }

    private suspend fun getMonthMostCategoryExpense() {
        getExpenseMonthMostCategoryUseCase(
            GetExpenseMonthMostCategoryUseCase.Params(
                _uiState.value.currentMonth.year,
                _uiState.value.currentMonth.monthValue
            )
        ).collectLatest { result ->
            when (result) {
                is DataResourceResult.Success -> {
                    if (result.data.isNotEmpty()) {
                        val mostCategoryExpense = result.data.first()
                        val layerModel =
                            AllowanceChartCategoryUIModelMapper.mapToLayerModel(mostCategoryExpense)
                        _uiState.update {
                            it.copy(
                                uiModel = it.uiModel.copy(
                                    mostCategoryExpense = layerModel
                                )
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                uiModel = it.uiModel.copy(
                                    mostCategoryExpense = null
                                )
                            )
                        }
                    }
                }

                is DataResourceResult.Failure -> {
                    _uiState.update {
                        it.copy(error = result.exception)
                    }
                }

                DataResourceResult.DummyConstructor, DataResourceResult.Loading -> {}
            }
        }
    }

    private suspend fun getMonthDailyAmounts() {
        getMonthDailyAmountsUseCase(
            GetMonthDailyAmountsUseCase.Params(
                _uiState.value.currentMonth.year,
                _uiState.value.currentMonth.monthValue
            )
        ).collectLatest { result ->
            when (result) {
                is DataResourceResult.Success -> {
                    _uiState.update {
                        it.copy(
                            uiModel = it.uiModel.copy(
                                monthlyDailyAmounts = result.data
                            )
                        )
                    }
                }

                is DataResourceResult.Failure -> {
                    _uiState.update {
                        it.copy(
                            error = result.exception
                        )
                    }
                }

                else -> {}
            }
        }
    }

    private fun getSelectedDayTransactions() {
        viewModelScope.launch {
            getSelectDayTransactionsUseCase(
                GetSelectDayTransactionsUseCase.Params(
                    _uiState.value.selectedDate
                )
            ).collectLatest { result ->
                when (result) {
                    is DataResourceResult.Success -> {
                        if (result.data.isNotEmpty()) {
                            val chartUIModels =
                                result.data.map { AllowanceChartUIModelMapper.mapToLayerModel(it) }
                            _uiState.update {
                                it.copy(
                                    uiModel = it.uiModel.copy(
                                        selectedDayTransactions = chartUIModels
                                    )
                                )
                            }
                        }
                    }

                    is DataResourceResult.Failure -> {
                        _uiState.update {
                            it.copy(
                                error = result.exception
                            )
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    fun getExpenseCategoryList() {
        viewModelScope.launch {
            getExpenseCategoryListUseCase().collectLatest { result ->
                when (result) {
                    is DataResourceResult.Success -> {
                        val categories = result.data.map { CategoryUIModelMapper.mapToLayerModel(it) }
                        _uiState.update {
                            it.copy(
                                uiModel = it.uiModel.copy(
                                    expenseCategories = categories
                                )
                            )
                        }
                    }

                    is DataResourceResult.Failure -> {
                        _uiState.update {
                            it.copy(
                                error = result.exception
                            )
                        }
                    }

                    DataResourceResult.DummyConstructor -> {}

                    DataResourceResult.Loading -> {}
                }
            }
        }

    }

    fun getIncomeCategoryList() {
        viewModelScope.launch {
            getIncomeCategoryListUseCase().collectLatest { result ->
                when (result) {
                    is DataResourceResult.Success -> {
                        val categories = result.data.map { CategoryUIModelMapper.mapToLayerModel(it) }
                        _uiState.update {
                            it.copy(
                                uiModel = it.uiModel.copy(
                                    incomeCategories = categories
                                )
                            )
                        }
                    }

                    is DataResourceResult.Failure -> {
                        _uiState.update {
                            it.copy(
                                error = result.exception
                            )
                        }
                    }

                    DataResourceResult.DummyConstructor -> {
                    }

                    DataResourceResult.Loading -> {}
                }
            }
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