package com.paykidscompose.common.usecase.allowance.expense

import com.paykidscompose.common.repositories.ExpenseAllowanceRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase

class DeleteExpenseUseCase(
    private val expenseAllowanceRepository: ExpenseAllowanceRepository
) : SuspendUseCase<DeleteExpenseUseCase.Params, DataResourceResult<Boolean>>() {

    override suspend fun execute(params: Params?): DataResourceResult<Boolean> {
        return if (params != null) {
            expenseAllowanceRepository.deleteExpense(params.id)
        } else {
            DataResourceResult.Failure(IllegalArgumentException("삭제할 소비 항목의 ID가 null이거나 잘못된 값입니다."))
        }
    }

    data class Params(
        val id: Int
    )
}