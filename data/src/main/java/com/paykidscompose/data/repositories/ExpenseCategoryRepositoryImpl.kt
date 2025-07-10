package com.paykidscompose.data.repositories

import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.data.model.allowance.CategoryDTO
import com.paykidscompose.data.network.NetworkModule
import com.paykidscompose.data.network.service.ExpenseCategoryApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ExpenseCategoryRepositoryImpl(private val categoryApiService: ExpenseCategoryApiService = NetworkModule.provideExpenseCategoryApiService()) {

    suspend fun deleteExpenseCategory(category: String): DataResourceResult<Boolean> {
        return runCatching {
            categoryApiService.deleteExpenseCategory(category)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data)},
            onFailure = { DataResourceResult.Failure(it)}
        )
    }

    suspend fun saveExpenseCategory(category: String, request: CategoryDTO): DataResourceResult<Boolean> {
        return runCatching {
            categoryApiService.saveExpenseCategory(category, request)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data)},
            onFailure = { DataResourceResult.Failure(it)}
        )
    }

    fun getExpenseCategoryList(): Flow<DataResourceResult<CategoryDTO>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            categoryApiService.getExpenseCategoryList()
        }.fold(
            onSuccess = { emit(DataResourceResult.Success(it.data))},
            onFailure = { emit(DataResourceResult.Failure(it))}
        )
    }
}