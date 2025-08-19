package com.paykidscompose.common.usecase.allowance.expense

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.model.allowance.AllowanceChartModel
import com.paykidscompose.common.repository.ExpenseAllowanceRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReplaceExpenseUseCase @Inject constructor(
    private val expenseAllowanceRepository: ExpenseAllowanceRepository
) : SuspendUseCase<ReplaceExpenseUseCase.Params, DataResourceResult<Boolean>>() {

    override suspend fun execute(params: Params?): DataResourceResult<Boolean> {
        return if (params != null) {
            expenseAllowanceRepository.replaceExpense(params.allowanceChart)
        } else {
            DataResourceResult.Failure(PayKidsException.ToastException(code = -1,"수정할 소비 정보가 존재하지 않습니다."))
        }
    }

    data class Params(
        val allowanceChart: AllowanceChartModel
    )
}