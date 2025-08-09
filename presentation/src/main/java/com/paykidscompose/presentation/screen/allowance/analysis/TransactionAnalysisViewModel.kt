package com.paykidscompose.presentation.screen.allowance.analysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paykidscompose.common.enums.AllowanceType
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.allowance.expense.DeleteExpenseCategoryUseCase
import com.paykidscompose.common.usecase.allowance.expense.GetExpenseCategoryListUseCase
import com.paykidscompose.common.usecase.allowance.expense.GetExpenseMonthAllCategoryUseCase
import com.paykidscompose.common.usecase.allowance.expense.GetExpenseMonthTotalAmountUseCase
import com.paykidscompose.common.usecase.allowance.expense.SaveExpenseCategoryUseCase
import com.paykidscompose.common.usecase.allowance.income.DeleteIncomeCategoryUseCase
import com.paykidscompose.common.usecase.allowance.income.GetIncomeCategoryListUseCase
import com.paykidscompose.common.usecase.allowance.income.GetIncomeMonthAllCategoryUseCase
import com.paykidscompose.common.usecase.allowance.income.GetIncomeMonthTotalAmountUseCase
import com.paykidscompose.common.usecase.allowance.income.SaveIncomeCategoryUseCase
import com.paykidscompose.common.util.today
import com.paykidscompose.presentation.base.UIEvent
import com.paykidscompose.presentation.base.UIState
import com.paykidscompose.presentation.mapper.allowance.AllowanceChartCategoryUIModelMapper
import com.paykidscompose.presentation.mapper.allowance.CategoryUIModelMapper
import com.paykidscompose.presentation.model.allowance.AllowanceChartCategoryUIModel
import com.paykidscompose.presentation.model.allowance.TransactionAnalysisUIModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class TransactionAnalysisViewModel(
    private val getExpenseMonthTotalAmountUseCase: GetExpenseMonthTotalAmountUseCase,
    private val getIncomeMonthTotalAmountUseCase: GetIncomeMonthTotalAmountUseCase,
    private val getExpenseMonthAllCategoryUseCase: GetExpenseMonthAllCategoryUseCase,
    private val getIncomeMonthAllCategoryUseCase: GetIncomeMonthAllCategoryUseCase,
    private val getExpenseCategoryListUseCase: GetExpenseCategoryListUseCase,
    private val getIncomeCategoryListUseCase: GetIncomeCategoryListUseCase,
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
                    getIncomeAllCategoryForMonth()
                }

                AllowanceType.EXPENSE -> {
                    getExpenseMonthTotalAmount()
                    getExpenseAllCategoryForMonth()
                }
            }
        }
    }

    fun onPrevMonth() {
        _uiState.update {
            it.copy(currentMonth = it.currentMonth.minusMonths(1))
        }

        load()
    }

    fun onNextMonth() {
        _uiState.update {
            it.copy(currentMonth = it.currentMonth.plusMonths(1))
        }

        load()
    }

    fun onAllowanceTypeSelected(type: AllowanceType) {
        _uiState.update {
            it.copy(
                selectedType = type
            )
        }

        load()
    }

    fun enterDeleteMode() {
        _uiState.update {
            it.copy(
                isDeleteMode = true
            )
        }
    }

    fun exitDeleteMode() {
        if(_uiState.value.selectedCategoryForDelete.isNotEmpty()) {
            viewModelScope.launch {
                when (_uiState.value.selectedType) {
                    AllowanceType.INCOME -> {
                        deleteIncomeCategory()
                        getIncomeMonthTotalAmount()
                        getIncomeAllCategoryForMonth()
                    }

                    AllowanceType.EXPENSE -> {
                        deleteExpenseCategory()
                        getExpenseMonthTotalAmount()
                        getExpenseAllCategoryForMonth()
                    }
                }
            }
        }

        _uiState.update {
            it.copy(
                isDeleteMode = false,
                selectedCategoryForDelete = emptyList()
            )
        }
    }

    fun onToggleCategorySelection(category: String) {
        _uiState.update {
            val current = it.selectedCategoryForDelete
            val updated = if (category in current) {
                current - category
            } else {
                current + category
            }

            it.copy(selectedCategoryForDelete = updated)
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

        load()
    }

    private suspend fun deleteExpenseCategory() {
        if (!_uiState.value.isDeleteMode || _uiState.value.selectedCategoryForDelete.isEmpty()) return

        _uiState.value.selectedCategoryForDelete.forEach { category ->
            val result = deleteExpenseCategoryUseCase(
                DeleteExpenseCategoryUseCase.Params(
                    category
                )
            )

            when (result) {
                is DataResourceResult.Success -> {
                    _uiState.update {
                        it.copy(
                            selectedCategoryForDelete = it.selectedCategoryForDelete - category
                        )
                    }

                    _uiEvent.emit(UIEvent.SuccessShowToast("$category 카테고리를 삭제했습니다!"))
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
    }

    private suspend fun deleteIncomeCategory() {
        if (!_uiState.value.isDeleteMode || _uiState.value.selectedCategoryForDelete.isEmpty()) return

        _uiState.value.selectedCategoryForDelete.forEach { category ->
            val result = deleteIncomeCategoryUseCase(
                DeleteIncomeCategoryUseCase.Params(
                    category
                )
            )

            when (result) {
                is DataResourceResult.Success -> {
                    _uiState.update {
                        it.copy(
                            selectedCategoryForDelete = it.selectedCategoryForDelete - category
                        )
                    }

                    _uiEvent.emit(UIEvent.SuccessShowToast("$category 카테고리를 삭제했습니다!"))
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
    }

    private fun saveExpenseCategory() {
        if (!_uiState.value.isAddCategory) return

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
        if (!_uiState.value.isAddCategory) return

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

    private suspend fun getExpenseAllCategoryForMonth() {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }

        combine(
            getExpenseCategoryListUseCase(),
            getExpenseMonthAllCategoryUseCase(
                GetExpenseMonthAllCategoryUseCase.Params(
                    _uiState.value.currentMonth.year,
                    _uiState.value.currentMonth.monthValue,
                )
            )
        ) { categoryResult, monthResult ->
            Pair(categoryResult, monthResult)
        }.collectLatest { (categoryResult, monthResult) ->
            when {
                categoryResult is DataResourceResult.Success && monthResult is DataResourceResult.Success -> {
                    val allCategories = categoryResult.data.map { CategoryUIModelMapper.mapToLayerModel(it) }
                    val monthCategory =
                        monthResult.data.map { AllowanceChartCategoryUIModelMapper.mapToLayerModel(it) }

                    val merge = allCategories.map { category ->
                        val match = monthCategory.find { it.category == category.title }
                        AllowanceChartCategoryUIModel(
                            category = category.title,
                            type = category.type,
                            amount = match?.amount ?: 0,
                            percent = match?.percent ?: 0
                        )
                    }

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            uiModel = it.uiModel.copy(
                                transactionList = merge
                            )
                        )
                    }
                }

                categoryResult is DataResourceResult.Failure -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = categoryResult.exception
                        )
                    }
                }

                monthResult is DataResourceResult.Failure -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = monthResult.exception
                        )
                    }
                }

                else -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private suspend fun getIncomeAllCategoryForMonth() {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }

        combine(
            getIncomeCategoryListUseCase(),
            getIncomeMonthAllCategoryUseCase(
                GetIncomeMonthAllCategoryUseCase.Params(
                    _uiState.value.currentMonth.year,
                    _uiState.value.currentMonth.monthValue,
                )
            )
        ) { categoryResult, monthResult ->
            Pair(categoryResult, monthResult)
        }.collectLatest { (categoryResult, monthResult) ->
            when {
                categoryResult is DataResourceResult.Success && monthResult is DataResourceResult.Success -> {
                    val allCategories = categoryResult.data.map { CategoryUIModelMapper.mapToLayerModel(it) }
                    val monthCategory =
                        monthResult.data.map { AllowanceChartCategoryUIModelMapper.mapToLayerModel(it) }

                    val merge = allCategories.map { category ->
                        val match = monthCategory.find { it.category == category.title }
                        AllowanceChartCategoryUIModel(
                            category = category.title,
                            type = category.type,
                            amount = match?.amount ?: 0,
                            percent = match?.percent ?: 0
                        )
                    }

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            uiModel = it.uiModel.copy(
                                transactionList = merge
                            )
                        )
                    }
                }

                categoryResult is DataResourceResult.Failure -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = categoryResult.exception
                        )
                    }
                }

                monthResult is DataResourceResult.Failure -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = monthResult.exception
                        )
                    }
                }

                else -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
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

data class TransactionAnalysisUIState(
    override val isLoading: Boolean = false,
    override val error: PayKidsException? = null,
    val currentMonth: LocalDate = today.withDayOfMonth(1),
    val uiModel: TransactionAnalysisUIModel,
    val totalAmount: Int = 0,
    val isAddCategory: Boolean = false,
    val isDeleteMode: Boolean = false,
    val category: String = "",
    val selectedType: AllowanceType = AllowanceType.EXPENSE,
    val selectedCategoryForDelete: List<String> = emptyList()
) : UIState()