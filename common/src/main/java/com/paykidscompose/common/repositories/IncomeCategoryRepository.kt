package com.paykidscompose.common.repositories

import com.paykidscompose.common.model.allowance.CategoryModel
import com.paykidscompose.common.result.DataResourceResult
import kotlinx.coroutines.flow.Flow

interface IncomeCategoryRepository {
    suspend fun deleteIncomeCategory(category: String): DataResourceResult<Boolean>
    suspend fun saveIncomeCategory(category: String): DataResourceResult<Boolean>
    fun getIncomeCategoryList(): Flow<DataResourceResult<List<CategoryModel>>>
}