package com.paykidscompose.common.repositories

import com.paykidscompose.common.model.allowance.CategoryModel
import com.paykidscompose.common.result.DataResourceResult
import kotlinx.coroutines.flow.Flow

interface ExpenseCategoryRepository {
    suspend fun deleteExpenseCategory(category: String): DataResourceResult<Boolean>
    suspend fun saveExpenseCategory(category: String): DataResourceResult<Boolean>
    fun getExpenseCategoryList(): Flow<DataResourceResult<List<CategoryModel>>>
}