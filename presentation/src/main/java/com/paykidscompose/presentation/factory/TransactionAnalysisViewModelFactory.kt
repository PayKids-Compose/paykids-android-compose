package com.paykidscompose.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.paykidscompose.common.usecase.allowance.expense.DeleteExpenseCategoryUseCase
import com.paykidscompose.common.usecase.allowance.expense.GetExpenseAllCategoryForMonthUseCase
import com.paykidscompose.common.usecase.allowance.expense.GetExpenseMonthTotalAmountUseCase
import com.paykidscompose.common.usecase.allowance.expense.SaveExpenseCategoryUseCase
import com.paykidscompose.common.usecase.allowance.income.DeleteIncomeCategoryUseCase
import com.paykidscompose.common.usecase.allowance.income.GetIncomeAllCategoryForMonthUseCase
import com.paykidscompose.common.usecase.allowance.income.GetIncomeMonthTotalAmountUseCase
import com.paykidscompose.common.usecase.allowance.income.SaveIncomeCategoryUseCase
import com.paykidscompose.presentation.screen.allowance.analysis.TransactionAnalysisViewModel

class TransactionAnalysisViewModelFactory(
    private val getExpenseMonthTotalAmountUseCase: GetExpenseMonthTotalAmountUseCase,
    private val getIncomeMonthTotalAmountUseCase: GetIncomeMonthTotalAmountUseCase,
    private val getExpenseAllCategoryForMonthUseCase: GetExpenseAllCategoryForMonthUseCase,
    private val getIncomeAllCategoryForMonthUseCase: GetIncomeAllCategoryForMonthUseCase,
    private val deleteExpenseCategoryUseCase: DeleteExpenseCategoryUseCase,
    private val deleteIncomeCategoryUseCase: DeleteIncomeCategoryUseCase,
    private val saveExpenseCategoryUseCase: SaveExpenseCategoryUseCase,
    private val saveIncomeCategoryUseCase: SaveIncomeCategoryUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionAnalysisViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TransactionAnalysisViewModel(
                getExpenseMonthTotalAmountUseCase,
                getIncomeMonthTotalAmountUseCase,
                getExpenseAllCategoryForMonthUseCase,
                getIncomeAllCategoryForMonthUseCase,
                deleteExpenseCategoryUseCase,
                deleteIncomeCategoryUseCase,
                saveExpenseCategoryUseCase,
                saveIncomeCategoryUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}