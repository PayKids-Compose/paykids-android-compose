package com.paykidscompose.common.usecase.allowance.income

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.model.allowance.AllowanceChartModel
import com.paykidscompose.common.repositories.IncomeAllowanceRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase

class ReplaceIncomeUseCase(
    private val repository: IncomeAllowanceRepository
) : SuspendUseCase<ReplaceIncomeUseCase.Params, DataResourceResult<Boolean>>() {

    override suspend fun execute(params: Params?): DataResourceResult<Boolean> {
        return if (params != null) {
            repository.replaceIncome(params.allowanceChart)
        } else {
            DataResourceResult.Failure(PayKidsException.ToastException(code = -1,"수정할 수입 정보를 제대로 입력하세요."))
        }
    }

    data class Params(
        val allowanceChart: AllowanceChartModel
    )
}