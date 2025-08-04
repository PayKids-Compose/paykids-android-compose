package com.paykidscompose.presentation.screens.allowance.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paykidscompose.common.enums.AllowanceType
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.allowance.expense.DeleteExpenseUseCase
import com.paykidscompose.common.usecase.allowance.expense.GetExpenseMonthCategoryUseCase
import com.paykidscompose.common.usecase.allowance.expense.ReplaceExpenseUseCase
import com.paykidscompose.common.usecase.allowance.expense.SaveExpenseUseCase
import com.paykidscompose.common.usecase.allowance.income.DeleteIncomeUseCase
import com.paykidscompose.common.usecase.allowance.income.GetIncomeMonthCategoryUseCase
import com.paykidscompose.common.usecase.allowance.income.ReplaceIncomeUseCase
import com.paykidscompose.common.usecase.allowance.income.SaveIncomeUseCase
import com.paykidscompose.presentation.base.UIEvent
import com.paykidscompose.presentation.base.UIState
import com.paykidscompose.presentation.mapper.allowance.AllowanceChartUIModelMapper
import com.paykidscompose.presentation.model.allowance.AllowanceChartUIModel
import com.paykidscompose.presentation.model.allowance.CategoryDetailUIModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryDetailViewModel(
    private val getExpenseMonthCategoryUseCase: GetExpenseMonthCategoryUseCase,
    private val getIncomeMonthCategoryUseCase: GetIncomeMonthCategoryUseCase,
    private val saveExpenseUseCase: SaveExpenseUseCase,
    private val saveIncomeUseCase: SaveIncomeUseCase,
    private val replaceExpenseUseCase: ReplaceExpenseUseCase,
    private val replaceIncomeUseCase: ReplaceIncomeUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val deleteIncomeUseCase: DeleteIncomeUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CategoryDetailUIState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun applyInitialArgs(year: Int, month: Int, category: String, type: AllowanceType) {
        _uiState.update {
            it.copy(
                uiModel = it.uiModel.copy(
                    year = year,
                    month = month,
                    category = category,
                    allowanceType = type
                )
            )
        }
    }

    fun load() {
        val uiModel = _uiState.value.uiModel
        if (uiModel.year == -1 && uiModel.month == -1 && uiModel.category == "") return

        _uiState.update {
            it.copy(
                isLoading = true
            )
        }

        viewModelScope.launch {
            when (uiModel.allowanceType) {
                AllowanceType.INCOME -> getIncomeMonthCategory()
                AllowanceType.EXPENSE -> getExpenseMonthCategory()
            }
        }

        _uiState.update {
            it.copy(
                isLoading = false
            )
        }
    }

    fun onAddClickDialog() {
        _uiState.update {
            it.copy(
                showAddTransactionDialog = true
            )
        }
    }

    fun onDismissAddTransactionDialog() {
        _uiState.update {
            it.copy(
                showAddTransactionDialog = false
            )
        }
    }

    fun onConfirmAddTransactionDialog(chartUIModel: AllowanceChartUIModel) {
        viewModelScope.launch {
            when (chartUIModel.type) {
                AllowanceType.INCOME -> {
                    saveIncome(chartUIModel)
                    getIncomeMonthCategory()
                }

                AllowanceType.EXPENSE -> {
                    saveExpense(chartUIModel)
                    getExpenseMonthCategory()
                }
            }
        }

        _uiState.update {
            it.copy(
                showAddTransactionDialog = false
            )
        }
    }

    fun onReplaceTransactionDialog(chartUIModel: AllowanceChartUIModel) {
        _uiState.update {
            it.copy(
                uiModel = it.uiModel.copy(
                    selectedTransaction = chartUIModel
                ),
                showReplaceTransactionDialog = true
            )
        }
    }

    fun onConfirmReplaceDialog(chartUIModel: AllowanceChartUIModel) {
        viewModelScope.launch {
            when (chartUIModel.type) {
                AllowanceType.INCOME -> {
                    replaceIncome(chartUIModel)
                    getIncomeMonthCategory()
                }

                AllowanceType.EXPENSE -> {
                    replaceExpense(chartUIModel)
                    getExpenseMonthCategory()
                }
            }
        }

        _uiState.update {
            it.copy(
                uiModel = it.uiModel.copy(
                    selectedTransaction = null
                ),
                showReplaceTransactionDialog = false
            )
        }
    }

    fun onDismissReplaceDialog() {
        _uiState.update {
            it.copy(
                uiModel = it.uiModel.copy(
                    selectedTransaction = null
                ),
                showReplaceTransactionDialog = false
            )
        }
    }

    fun onDeleteTransactionDialog(chartUIModel: AllowanceChartUIModel) {
        _uiState.update {
            it.copy(
                uiModel = it.uiModel.copy(
                    selectedTransaction = chartUIModel
                ),

                showDeleteTransactionDialog = true
            )
        }
    }

    fun onConfirmDeleteDialog() {
        viewModelScope.launch {
            when (_uiState.value.uiModel.allowanceType) {
                AllowanceType.INCOME -> {
                    deleteIncome()
                    getIncomeMonthCategory()
                }

                AllowanceType.EXPENSE -> {
                    deleteExpense()
                    getExpenseMonthCategory()
                }
            }
        }

        _uiState.update {
            it.copy(
                uiModel = it.uiModel.copy(
                    selectedTransaction = null
                ),

                showDeleteTransactionDialog = false
            )
        }
    }

    fun onDismissDeleteDialog() {
        _uiState.update {
            it.copy(
                uiModel = it.uiModel.copy(
                    selectedTransaction = null
                ),

                showDeleteTransactionDialog = false
            )
        }
    }

    private suspend fun saveExpense(chartUIModel: AllowanceChartUIModel) {
        val result = saveExpenseUseCase(
            SaveExpenseUseCase.Params(
                AllowanceChartUIModelMapper.mapToModel(chartUIModel)
            )
        )

        when (result) {
            is DataResourceResult.Success -> {
                _uiEvent.emit(UIEvent.SuccessShowToast("수입 내역이 저장되었습니다!"))
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

    private suspend fun saveIncome(chartUIModel: AllowanceChartUIModel) {
        val result = saveIncomeUseCase(
            SaveIncomeUseCase.Params(
                AllowanceChartUIModelMapper.mapToModel(chartUIModel)
            )
        )

        when (result) {
            is DataResourceResult.Success -> {
                _uiEvent.emit(UIEvent.SuccessShowToast("소비 내역이 저장되었습니다!"))
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
                AllowanceChartUIModelMapper.mapToModel(chartUIModel)
            )
        )

        when (result) {
            is DataResourceResult.Success -> {
                _uiEvent.emit(UIEvent.SuccessShowToast("소비 내역이 수정되었습니다!"))
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

    private suspend fun replaceIncome(chartUIModel: AllowanceChartUIModel) {
        val result = replaceIncomeUseCase(
            ReplaceIncomeUseCase.Params(
                AllowanceChartUIModelMapper.mapToModel(chartUIModel)
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

    private suspend fun deleteExpense() {
        val result = deleteExpenseUseCase(
            DeleteExpenseUseCase.Params(
                _uiState.value.uiModel.selectedTransaction?.id ?: return
            )
        )

        when (result) {
            is DataResourceResult.Success -> {
                if (result.data) {
                    _uiEvent.emit(UIEvent.SuccessShowToast("선택한 소비 내역이 삭제되었습니다!"))
                }
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

    private suspend fun deleteIncome() {
        val result = deleteIncomeUseCase(
            DeleteIncomeUseCase.Params(
                _uiState.value.uiModel.selectedTransaction?.id ?: return
            )
        )

        when (result) {
            is DataResourceResult.Success -> {
                if (result.data) {
                    _uiEvent.emit(UIEvent.SuccessShowToast("선택한 수입 내역이 삭제되었습니다!"))
                }
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

    private suspend fun getExpenseMonthCategory() {
        getExpenseMonthCategoryUseCase(
            GetExpenseMonthCategoryUseCase.Params(
                _uiState.value.uiModel.year,
                _uiState.value.uiModel.month,
                _uiState.value.uiModel.category,
            )
        ).collectLatest { result ->
            when (result) {
                is DataResourceResult.Success -> {
                    val chartUIModels = result.data.map { AllowanceChartUIModelMapper.mapToLayerModel(it) }
                    val totalAmount = result.data.sumOf { it.amount }

                    _uiState.update {
                        it.copy(
                            uiModel = it.uiModel.copy(
                                totalAmount = totalAmount,
                                detailCategories = chartUIModels
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

                DataResourceResult.DummyConstructor, DataResourceResult.Loading -> {}
            }
        }
    }

    private suspend fun getIncomeMonthCategory() {
        getIncomeMonthCategoryUseCase(
            GetIncomeMonthCategoryUseCase.Params(
                _uiState.value.uiModel.year,
                _uiState.value.uiModel.month,
                _uiState.value.uiModel.category,
            )
        ).collectLatest { result ->
            when (result) {
                is DataResourceResult.Success -> {
                    val chartUIModels = result.data.map { AllowanceChartUIModelMapper.mapToLayerModel(it) }
                    val totalAmount = result.data.sumOf { it.amount }

                    _uiState.update {
                        it.copy(
                            uiModel = it.uiModel.copy(
                                totalAmount = totalAmount,
                                detailCategories = chartUIModels
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

                DataResourceResult.DummyConstructor, DataResourceResult.Loading -> {}
            }
        }
    }


    fun clearError() {
        _uiState.update {
            it.copy(
                error = null
            )
        }
    }
}

data class CategoryDetailUIState(
    override val isLoading: Boolean = false,
    override val error: PayKidsException? = null,
    val uiModel: CategoryDetailUIModel = CategoryDetailUIModel(),
    val showAddTransactionDialog: Boolean = false,
    val showReplaceTransactionDialog: Boolean = false,
    val showDeleteTransactionDialog: Boolean = false
) : UIState()