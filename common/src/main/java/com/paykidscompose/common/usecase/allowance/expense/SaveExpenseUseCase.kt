package com.paykidscompose.common.usecase.allowance.expense

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.model.allowance.AllowanceChartModel
import com.paykidscompose.common.repositories.ExpenseAllowanceRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase

class SaveExpenseUseCase(
    private val expenseAllowanceRepository: ExpenseAllowanceRepository
) : SuspendUseCase<SaveExpenseUseCase.Params, DataResourceResult<Boolean>>() {

    override suspend fun execute(params: Params?): DataResourceResult<Boolean> {
        return if (params != null) {
            expenseAllowanceRepository.saveExpense(params.allowanceChart)
        } else {
            DataResourceResult.Failure(PayKidsException.ToastException(code = -1,"저장할 소비 정보가 유효하지 않습니다."))
        }
    }

    data class Params(
        val allowanceChart: AllowanceChartModel
    )
}