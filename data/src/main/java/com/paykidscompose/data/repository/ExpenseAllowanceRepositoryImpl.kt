package com.paykidscompose.data.repository

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.model.allowance.AllowanceChartAmountModel
import com.paykidscompose.common.model.allowance.AllowanceChartCategoryModel
import com.paykidscompose.common.model.allowance.AllowanceChartModel
import com.paykidscompose.common.repository.ExpenseAllowanceRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.data.mapper.allowance.AllowanceChartAmountMapper
import com.paykidscompose.data.mapper.allowance.AllowanceChartCategoryMapper
import com.paykidscompose.data.mapper.allowance.AllowanceChartMapper
import com.paykidscompose.data.network.service.ExpenseAllowanceApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Inject

class ExpenseAllowanceRepositoryImpl @Inject constructor(
    private val expenseApiService: ExpenseAllowanceApiService
) : ExpenseAllowanceRepository {

    override suspend fun deleteExpense(id: Long): DataResourceResult<Boolean> {
        return runCatching {
            expenseApiService.deleteExpense(id)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = {
                DataResourceResult.Failure(
                    PayKidsException.ToastException(
                        code = -1,
                        message = it.message ?: ""
                    )
                )
            }
        )
    }

    override suspend fun saveExpense(request: AllowanceChartModel): DataResourceResult<Boolean> {
        return runCatching {
            val dto = AllowanceChartMapper.mapToLayerModel(request)
            expenseApiService.saveExpense(dto)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = {
                DataResourceResult.Failure(
                    PayKidsException.ToastException(
                        code = -1,
                        message = it.message ?: ""
                    )
                )
            }
        )
    }

    override suspend fun replaceExpense(request: AllowanceChartModel): DataResourceResult<Boolean> {
        return runCatching {
            val dto = AllowanceChartMapper.mapToLayerModel(request)
            expenseApiService.replaceExpense(dto)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = {
                DataResourceResult.Failure(
                    PayKidsException.ToastException(
                        code = -1,
                        message = it.message ?: ""
                    )
                )
            }
        )
    }

    override suspend fun getExpenseMonthTotalAmount(
        year: Int,
        month: Int
    ): DataResourceResult<Int> {
        return runCatching {
            expenseApiService.getExpenseMonthTotalAmount(year, month)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = {
                DataResourceResult.Failure(
                    PayKidsException.ToastException(
                        code = -1,
                        message = it.message ?: ""
                    )
                )
            }
        )
    }

    override fun getExpenseMonthMostCategory(
        year: Int,
        month: Int
    ): Flow<DataResourceResult<List<AllowanceChartCategoryModel>>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            expenseApiService.getExpenseMonthMostCategory(year, month)
        }.fold(
            onSuccess = {
                val models = it.data.map { dto ->
                    AllowanceChartCategoryMapper.mapToModel(dto)
                }
                emit(DataResourceResult.Success(models))
            },
            onFailure = {
                emit(
                    DataResourceResult.Failure(
                        PayKidsException.ToastException(
                            code = -1,
                            message = it.message ?: ""
                        )
                    )
                )
            }
        )
    }

    override fun getExpenseMonthDailyAmount(
        year: Int,
        month: Int
    ): Flow<DataResourceResult<List<AllowanceChartAmountModel>>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            expenseApiService.getExpenseMonthDailyAmount(year, month)
        }.fold(
            onSuccess = {
                val models = it.data.map { dto ->
                    AllowanceChartAmountMapper.mapToModel(dto)
                }
                emit(DataResourceResult.Success(models))
            },
            onFailure = {
                emit(
                    DataResourceResult.Failure(
                        PayKidsException.ToastException(
                            code = -1,
                            message = it.message ?: ""
                        )
                    )
                )
            }
        )
    }

    override fun getExpenseMonthCategory(
        year: Int,
        month: Int,
        category: String
    ): Flow<DataResourceResult<List<AllowanceChartModel>>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            expenseApiService.getExpenseMonthCategory(year, month, category)
        }.fold(
            onSuccess = {
                val models = it.data.map { dto ->
                    AllowanceChartMapper.mapToModel(dto)
                }
                emit(DataResourceResult.Success(models))
            },
            onFailure = {
                emit(
                    DataResourceResult.Failure(
                        PayKidsException.ToastException(
                            code = -1,
                            message = it.message ?: ""
                        )
                    )
                )
            }
        )
    }

    override fun getExpenseMonthAllCategory(
        year: Int,
        month: Int
    ): Flow<DataResourceResult<List<AllowanceChartCategoryModel>>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            expenseApiService.getExpenseMonthAllCategory(year, month)
        }.fold(
            onSuccess = {
                val models = it.data.map { dto ->
                    AllowanceChartCategoryMapper.mapToModel(dto)
                }
                emit(DataResourceResult.Success(models))
            },
            onFailure = {
                emit(
                    DataResourceResult.Failure(
                        PayKidsException.ToastException(
                            code = -1,
                            message = it.message ?: ""
                        )
                    )
                )
            }
        )
    }

    override fun getExpenseDay(
        localDate: LocalDate
    ): Flow<DataResourceResult<List<AllowanceChartModel>>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            expenseApiService.getExpenseDay(localDate.toString())
        }.fold(
            onSuccess = {
                val models = it.data.map { dto ->
                    AllowanceChartMapper.mapToModel(dto)
                }
                emit(DataResourceResult.Success(models))
            },
            onFailure = {
                emit(
                    DataResourceResult.Failure(
                        PayKidsException.ToastException(
                            code = -1,
                            message = it.message ?: ""
                        )
                    )
                )
            }
        )
    }
}