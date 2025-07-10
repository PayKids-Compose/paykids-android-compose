package com.paykidscompose.data.repositories

import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.data.model.allowance.AllowanceChartAmountDTO
import com.paykidscompose.data.model.allowance.AllowanceChartCategoryDTO
import com.paykidscompose.data.model.allowance.AllowanceChartDTO
import com.paykidscompose.data.network.NetworkModule
import com.paykidscompose.data.network.service.IncomeAllowanceApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class IncomeAllowanceRepositoryImpl(private val incomeAllowanceApiService: IncomeAllowanceApiService = NetworkModule.provideIncomeApiService()) {

    suspend fun deleteIncome(id: Int): DataResourceResult<Boolean> {
        return runCatching {
            incomeAllowanceApiService.deleteIncome(id)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    suspend fun saveIncome(request: AllowanceChartDTO): DataResourceResult<Boolean> {
        return runCatching {
            incomeAllowanceApiService.saveIncome(request)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    suspend fun replaceIncome(request: AllowanceChartDTO): DataResourceResult<Boolean> {
        return runCatching {
            incomeAllowanceApiService.replaceIncome(request)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    suspend fun getIncomeMonthTotalAmount(year: Int, month: Int): DataResourceResult<Int> {
        return runCatching {
            incomeAllowanceApiService.getIncomeMonthTotalAmount(year, month)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    fun getIncomeMonthDailyAmount(
        year: Int,
        month: Int
    ): Flow<DataResourceResult<List<AllowanceChartAmountDTO>>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            incomeAllowanceApiService.getIncomeMonthDailyAmount(year, month)
        }.fold(
            onSuccess = { emit(DataResourceResult.Success(it.data)) },
            onFailure = { emit(DataResourceResult.Failure(it)) }
        )
    }

    fun getIncomeMonthCategory(
        year: Int,
        month: Int,
        category: String
    ): Flow<DataResourceResult<List<AllowanceChartDTO>>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            incomeAllowanceApiService.getIncomeMonthCategory(year, month, category)
        }.fold(
            onSuccess = { emit(DataResourceResult.Success(it.data)) },
            onFailure = { emit(DataResourceResult.Failure(it)) }
        )
    }

    fun getIncomeMonthAllCategory(
        year: Int,
        month: Int
    ): Flow<DataResourceResult<List<AllowanceChartCategoryDTO>>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            incomeAllowanceApiService.getIncomeMonthAllCategory(year, month)
        }.fold(
            onSuccess = { emit(DataResourceResult.Success(it.data)) },
            onFailure = { emit(DataResourceResult.Failure(it)) }
        )
    }

    fun getIncomeDay(
        localDate: String
    ): Flow<DataResourceResult<List<AllowanceChartDTO>>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            incomeAllowanceApiService.getIncomeDay(localDate)
        }.fold(
            onSuccess = { emit(DataResourceResult.Success(it.data)) },
            onFailure = { emit(DataResourceResult.Failure(it)) }
        )
    }
}