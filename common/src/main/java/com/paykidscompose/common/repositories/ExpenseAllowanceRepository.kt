package com.paykidscompose.common.repositories

import com.paykidscompose.common.model.allowance.AllowanceChartAmountModel
import com.paykidscompose.common.model.allowance.AllowanceChartCategoryModel
import com.paykidscompose.common.model.allowance.AllowanceChartModel
import com.paykidscompose.common.result.DataResourceResult
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface ExpenseAllowanceRepository {
    suspend fun deleteExpense(id: Int): DataResourceResult<Boolean>
    suspend fun saveExpense(request: AllowanceChartModel): DataResourceResult<Boolean>
    suspend fun replaceExpense(request: AllowanceChartModel): DataResourceResult<Boolean>
    suspend fun getExpenseMonthTotalAmount(year: Int, month: Int): DataResourceResult<Int>
    fun getExpenseMonthMostCategory(
        year: Int,
        month: Int
    ): Flow<DataResourceResult<List<AllowanceChartCategoryModel>>>

    fun getExpenseMonthDailyAmount(
        year: Int,
        month: Int
    ): Flow<DataResourceResult<List<AllowanceChartAmountModel>>>

    fun getExpenseMonthCategory(
        year: Int,
        month: Int,
        category: String
    ): Flow<DataResourceResult<List<AllowanceChartModel>>>

    fun getExpenseMonthAllCategory(
        year: Int,
        month: Int
    ): Flow<DataResourceResult<List<AllowanceChartCategoryModel>>>

    fun getExpenseDay(
        localDate: LocalDate
    ): Flow<DataResourceResult<List<AllowanceChartModel>>>

}