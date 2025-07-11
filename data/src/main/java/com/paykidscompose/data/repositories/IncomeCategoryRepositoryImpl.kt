package com.paykidscompose.data.repositories

import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.data.model.allowance.CategoryDTO
import com.paykidscompose.data.network.NetworkModule
import com.paykidscompose.data.network.service.IncomeCategoryApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class IncomeCategoryRepositoryImpl(private val incomeCategoryApiService: IncomeCategoryApiService = NetworkModule.provideIncomeCategoryApiService()) {

    suspend fun deleteIncomeCategory(category: String): DataResourceResult<Boolean> {
        return runCatching {
            incomeCategoryApiService.deleteIncomeCategory(category)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    suspend fun saveIncomeCategory(category: String): DataResourceResult<Boolean> {
        return runCatching {
            incomeCategoryApiService.saveIncomeCategory(category)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    fun getIncomeCategoryList(): Flow<DataResourceResult<List<CategoryDTO>>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            incomeCategoryApiService.getIncomeCategoryList()
        }.fold(
            onSuccess = { emit(DataResourceResult.Success(it.data)) },
            onFailure = { emit(DataResourceResult.Failure(it)) }
        )
    }
}