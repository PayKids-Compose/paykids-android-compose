package com.paykidscompose.data.repositories

import com.paykidscompose.common.model.allowance.CategoryModel
import com.paykidscompose.common.repositories.ExpenseCategoryRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.data.mapper.allowance.CategoryMapper
import com.paykidscompose.data.network.NetworkModule
import com.paykidscompose.data.network.service.ExpenseCategoryApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ExpenseCategoryRepositoryImpl(
    private val categoryApiService: ExpenseCategoryApiService
) : ExpenseCategoryRepository {

    override suspend fun deleteExpenseCategory(category: String): DataResourceResult<Boolean> {
        return runCatching {
            categoryApiService.deleteExpenseCategory(category)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    override suspend fun saveExpenseCategory(category: String): DataResourceResult<Boolean> {
        return runCatching {
            categoryApiService.saveExpenseCategory(category)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    override fun getExpenseCategoryList(): Flow<DataResourceResult<List<CategoryModel>>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            categoryApiService.getExpenseCategoryList()
        }.fold(
            onSuccess = {
                val models = it.data.map { dto ->
                    CategoryMapper.mapToModel(dto)
                }
                emit(DataResourceResult.Success(models))
            },
            onFailure = { emit(DataResourceResult.Failure(it)) }
        )
    }
}