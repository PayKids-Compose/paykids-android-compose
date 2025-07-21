package com.paykidscompose.common.usecase.allowance.expense

import com.paykidscompose.common.repositories.ExpenseCategoryRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase

class SaveExpenseCategoryUseCase(
    private val repository: ExpenseCategoryRepository
) : SuspendUseCase<SaveExpenseCategoryUseCase.Params, DataResourceResult<Boolean>>() {

    override suspend fun execute(params: Params?): DataResourceResult<Boolean> {
        return if (params != null) {
            repository.saveExpenseCategory(params.category)
        } else {
            DataResourceResult.Failure(IllegalArgumentException("저장할 소비 카테고리를 제대로 입력하세요."))
        }
    }

    data class Params(
        val category: String
    )
}