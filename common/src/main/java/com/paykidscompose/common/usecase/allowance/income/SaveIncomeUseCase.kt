package com.paykidscompose.common.usecase.allowance.income

import com.paykidscompose.common.model.allowance.AllowanceChartModel
import com.paykidscompose.common.repositories.IncomeAllowanceRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase

class SaveIncomeUseCase(
    private val repository: IncomeAllowanceRepository
) : SuspendUseCase<SaveIncomeUseCase.Params, DataResourceResult<Boolean>>() {

    override suspend fun execute(params: Params?): DataResourceResult<Boolean> {
        return if (params != null) {
            repository.saveIncome(params.allowanceChart)
        } else {
            DataResourceResult.Failure(IllegalArgumentException("저장할 수입 정보가 유효하지 않습니다."))
        }
    }

    data class Params(
        val allowanceChart: AllowanceChartModel
    )
}