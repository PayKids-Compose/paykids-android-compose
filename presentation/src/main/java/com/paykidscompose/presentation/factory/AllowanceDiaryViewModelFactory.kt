package com.paykidscompose.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
import com.paykidscompose.presentation.screen.allowance.AllowanceDiaryViewModel

class AllowanceDiaryViewModelFactory(
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
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllowanceDiaryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AllowanceDiaryViewModel(
                getExpenseMonthTotalAmountUseCase,
                getExpenseMonthMostCategoryUseCase,
                getExpenseCategoryListUseCase,
                getIncomeCategoryListUseCase,
                getMonthDailyAmountsUseCase,
                getSelectDayTransactionsUseCase,
                saveExpenseUseCase,
                saveIncomeUseCase,
                replaceExpenseUseCase,
                replaceIncomeUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}