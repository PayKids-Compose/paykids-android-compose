package com.paykidscompose.data.repositories

import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.data.model.allowance.AllowanceChartAmountDTO
import com.paykidscompose.data.model.allowance.AllowanceChartCategoryDTO
import com.paykidscompose.data.model.allowance.AllowanceChartDTO
import com.paykidscompose.data.network.NetworkModule
import com.paykidscompose.data.network.service.ExpenseAllowanceApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ExpenseAllowanceRepositoryImpl(private val expenseApiService: ExpenseAllowanceApiService = NetworkModule.provideExpenseApiService()) {

    suspend fun deleteExpense(id: Int): DataResourceResult<Boolean> {
        return runCatching {
            expenseApiService.deleteExpense(id)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    suspend fun saveExpense(request: AllowanceChartDTO): DataResourceResult<Boolean> {
        return runCatching {
            expenseApiService.saveExpense(request)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    suspend fun replaceExpense(request: AllowanceChartDTO): DataResourceResult<Boolean> {
        return runCatching {
            expenseApiService.replaceExpense(request)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    suspend fun getExpenseMonthTotalAmount(year: Int, month: Int): DataResourceResult<Int> {
        return runCatching {
            expenseApiService.getExpenseMonthTotalAmount(year, month)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    fun getExpenseMonthMostCategory(
        year: Int,
        month: Int
    ): Flow<DataResourceResult<List<AllowanceChartCategoryDTO>>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            expenseApiService.getExpenseMonthMostCategory(year, month)
        }.fold(
            onSuccess = { emit(DataResourceResult.Success(it.data)) },
            onFailure = { emit(DataResourceResult.Failure(it)) }
        )
    }

    fun getExpenseMonthDailyAmount(
        year: Int,
        month: Int
    ): Flow<DataResourceResult<List<AllowanceChartAmountDTO>>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            expenseApiService.getExpenseMonthDailyAmount(year, month)
        }.fold(
            onSuccess = { emit(DataResourceResult.Success(it.data)) },
            onFailure = { emit(DataResourceResult.Failure(it)) }
        )
    }

    fun getExpenseMonthCategory(
        year: Int,
        month: Int,
        category: String
    ): Flow<DataResourceResult<List<AllowanceChartDTO>>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            expenseApiService.getExpenseMonthCategory(year, month, category)
        }.fold(
            onSuccess = { emit(DataResourceResult.Success(it.data)) },
            onFailure = { emit(DataResourceResult.Failure(it)) }
        )
    }

    fun getExpenseMonthAllCategory(
        year: Int,
        month: Int
    ): Flow<DataResourceResult<List<AllowanceChartCategoryDTO>>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            expenseApiService.getExpenseMonthAllCategory(year, month)
        }.fold(
            onSuccess = { emit(DataResourceResult.Success(it.data)) },
            onFailure = { emit(DataResourceResult.Failure(it)) }
        )
    }

    fun getExpenseDay(
        localDate: String
    ): Flow<DataResourceResult<List<AllowanceChartDTO>>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            expenseApiService.getExpenseDay(localDate)
        }.fold(
            onSuccess = { emit(DataResourceResult.Success(it.data)) },
            onFailure = { emit(DataResourceResult.Failure(it)) }
        )
    }
}