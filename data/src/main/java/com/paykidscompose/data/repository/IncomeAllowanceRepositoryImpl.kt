package com.paykidscompose.data.repository

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.model.allowance.AllowanceChartAmountModel
import com.paykidscompose.common.model.allowance.AllowanceChartCategoryModel
import com.paykidscompose.common.model.allowance.AllowanceChartModel
import com.paykidscompose.common.repositories.IncomeAllowanceRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.data.mapper.allowance.AllowanceChartAmountMapper
import com.paykidscompose.data.mapper.allowance.AllowanceChartCategoryMapper
import com.paykidscompose.data.mapper.allowance.AllowanceChartMapper
import com.paykidscompose.data.network.service.IncomeAllowanceApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate

class IncomeAllowanceRepositoryImpl(
    private val incomeAllowanceApiService: IncomeAllowanceApiService
) : IncomeAllowanceRepository {

    override suspend fun deleteIncome(id: Long): DataResourceResult<Boolean> {
        return runCatching {
            incomeAllowanceApiService.deleteIncome(id)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(PayKidsException.ToastException(code = -1, message = it.message ?: "")) }
        )
    }

    override suspend fun saveIncome(request: AllowanceChartModel): DataResourceResult<Boolean> {
        return runCatching {
            val dto = AllowanceChartMapper.mapToLayerModel(request)
            incomeAllowanceApiService.saveIncome(dto)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(PayKidsException.ToastException(code = -1, message = it.message ?: "")) }
        )
    }

    override suspend fun replaceIncome(request: AllowanceChartModel): DataResourceResult<Boolean> {
        return runCatching {
            val dto = AllowanceChartMapper.mapToLayerModel(request)
            incomeAllowanceApiService.replaceIncome(dto)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(PayKidsException.ToastException(code = -1, message = it.message ?: "")) }
        )
    }

    override suspend fun getIncomeMonthTotalAmount(year: Int, month: Int): DataResourceResult<Int> {
        return runCatching {
            incomeAllowanceApiService.getIncomeMonthTotalAmount(year, month)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(PayKidsException.ToastException(code = -1, message = it.message ?: "")) }
        )
    }

    override fun getIncomeMonthDailyAmount(
        year: Int,
        month: Int
    ): Flow<DataResourceResult<List<AllowanceChartAmountModel>>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            incomeAllowanceApiService.getIncomeMonthDailyAmount(year, month)
        }.fold(
            onSuccess = {
                val models = it.data.map { dto ->
                    AllowanceChartAmountMapper.mapToModel(dto)
                }
                emit(DataResourceResult.Success(models))
            },
            onFailure = { emit(DataResourceResult.Failure(PayKidsException.ToastException(code = -1, message = it.message ?: ""))) }
        )
    }

    override fun getIncomeMonthCategory(
        year: Int,
        month: Int,
        category: String
    ): Flow<DataResourceResult<List<AllowanceChartModel>>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            incomeAllowanceApiService.getIncomeMonthCategory(year, month, category)
        }.fold(
            onSuccess = {
                val models = it.data.map { dto ->
                    AllowanceChartMapper.mapToModel(dto)
                }
                emit(DataResourceResult.Success(models))
            },
            onFailure = { emit(DataResourceResult.Failure(PayKidsException.ToastException(code = -1, message = it.message ?: ""))) }
        )
    }

    override fun getIncomeMonthAllCategory(
        year: Int,
        month: Int
    ): Flow<DataResourceResult<List<AllowanceChartCategoryModel>>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            incomeAllowanceApiService.getIncomeMonthAllCategory(year, month)
        }.fold(
            onSuccess = {
                val models = it.data.map { dto ->
                    AllowanceChartCategoryMapper.mapToModel(dto)
                }
                emit(DataResourceResult.Success(models))
            },
            onFailure = { emit(DataResourceResult.Failure(PayKidsException.ToastException(code = -1, message = it.message ?: ""))) }
        )
    }

    override fun getIncomeDay(
        localDate: LocalDate
    ): Flow<DataResourceResult<List<AllowanceChartModel>>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            incomeAllowanceApiService.getIncomeDay(localDate.toString())
        }.fold(
            onSuccess = {
                val models = it.data.map { dto ->
                    AllowanceChartMapper.mapToModel(dto)
                }
                emit(DataResourceResult.Success(models))
            },
            onFailure = { emit(DataResourceResult.Failure(PayKidsException.ToastException(code = -1, message = it.message ?: ""))) }
        )
    }
}