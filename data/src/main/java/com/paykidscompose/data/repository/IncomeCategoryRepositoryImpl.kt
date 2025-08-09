package com.paykidscompose.data.repository

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.model.allowance.CategoryModel
import com.paykidscompose.common.repositories.IncomeCategoryRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.data.mapper.allowance.CategoryMapper
import com.paykidscompose.data.network.service.IncomeCategoryApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class IncomeCategoryRepositoryImpl(
    private val incomeCategoryApiService: IncomeCategoryApiService
) : IncomeCategoryRepository {

    override suspend fun deleteIncomeCategory(category: String): DataResourceResult<Boolean> {
        return runCatching {
            incomeCategoryApiService.deleteIncomeCategory(category)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(PayKidsException.ToastException(code = -1, message = it.message ?: "")) }
        )
    }

    override suspend fun saveIncomeCategory(category: String): DataResourceResult<Boolean> {
        return runCatching {
            incomeCategoryApiService.saveIncomeCategory(category)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(PayKidsException.ToastException(code = -1, message = it.message ?: "")) }
        )
    }

    override fun getIncomeCategoryList(): Flow<DataResourceResult<List<CategoryModel>>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            incomeCategoryApiService.getIncomeCategoryList()
        }.fold(
            onSuccess = {
                val models = it.data.map { dto ->
                    CategoryMapper.mapToModel(dto)
                }
                emit(DataResourceResult.Success(models))
            },
            onFailure = { emit(DataResourceResult.Failure(PayKidsException.ToastException(code = -1, message = it.message ?: ""))) }
        )
    }
}