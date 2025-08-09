package com.paykidscompose.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.paykidscompose.common.usecase.allowance.expense.DeleteExpenseUseCase
import com.paykidscompose.common.usecase.allowance.expense.GetExpenseMonthCategoryUseCase
import com.paykidscompose.common.usecase.allowance.expense.ReplaceExpenseUseCase
import com.paykidscompose.common.usecase.allowance.expense.SaveExpenseUseCase
import com.paykidscompose.common.usecase.allowance.income.DeleteIncomeUseCase
import com.paykidscompose.common.usecase.allowance.income.GetIncomeMonthCategoryUseCase
import com.paykidscompose.common.usecase.allowance.income.ReplaceIncomeUseCase
import com.paykidscompose.common.usecase.allowance.income.SaveIncomeUseCase
import com.paykidscompose.presentation.screen.allowance.detail.CategoryDetailViewModel

class CategoryDetailViewModelFactory(
    private val getExpenseMonthCategoryUseCase: GetExpenseMonthCategoryUseCase,
    private val getIncomeMonthCategoryUseCase: GetIncomeMonthCategoryUseCase,
    private val saveExpenseUseCase: SaveExpenseUseCase,
    private val saveIncomeUseCase: SaveIncomeUseCase,
    private val replaceExpenseUseCase: ReplaceExpenseUseCase,
    private val replaceIncomeUseCase: ReplaceIncomeUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val deleteIncomeUseCase: DeleteIncomeUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryDetailViewModel(
                getExpenseMonthCategoryUseCase,
                getIncomeMonthCategoryUseCase,
                saveExpenseUseCase,
                saveIncomeUseCase,
                replaceExpenseUseCase,
                replaceIncomeUseCase,
                deleteExpenseUseCase,
                deleteIncomeUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}