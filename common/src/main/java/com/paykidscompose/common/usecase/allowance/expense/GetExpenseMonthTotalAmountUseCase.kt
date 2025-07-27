package com.paykidscompose.common.usecase.allowance.expense

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.repositories.ExpenseAllowanceRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase

class GetExpenseMonthTotalAmountUseCase(
    private val expenseAllowanceRepository: ExpenseAllowanceRepository
) : SuspendUseCase<GetExpenseMonthTotalAmountUseCase.Params, DataResourceResult<Int>>() {

    override suspend fun execute(params: Params?): DataResourceResult<Int> {
        return if (params != null) {
            expenseAllowanceRepository.getExpenseMonthTotalAmount(params.year, params.month)
        } else {
            DataResourceResult.Failure(PayKidsException.ToastException(code = -1,"조회할 날짜 정보를 입력해주세요. "))
        }
    }

    data class Params(
        val year: Int,
        val month: Int
    )
}