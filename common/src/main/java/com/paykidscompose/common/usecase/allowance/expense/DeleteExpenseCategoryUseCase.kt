package com.paykidscompose.common.usecase.allowance.expense

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.repository.ExpenseCategoryRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteExpenseCategoryUseCase @Inject constructor(
    private val repository: ExpenseCategoryRepository
) : SuspendUseCase<DeleteExpenseCategoryUseCase.Params, DataResourceResult<Boolean>>() {

    override suspend fun execute(params: Params?): DataResourceResult<Boolean> {
        return if (params != null) {
            repository.deleteExpenseCategory(params.category)
        } else {
            DataResourceResult.Failure(PayKidsException.ToastException(code = -1, message = "삭제할 소비 카테고리를 제대로 입력하세요."))
        }
    }

    data class Params(
        val category: String
    )
}