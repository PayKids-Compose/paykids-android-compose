package com.paykidscompose.common.usecase.allowance.income

import com.paykidscompose.common.repositories.IncomeAllowanceRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase

class DeleteIncomeUseCase(
    private val repository: IncomeAllowanceRepository
) : SuspendUseCase<DeleteIncomeUseCase.Params, DataResourceResult<Boolean>>() {

    override suspend fun execute(params: Params?): DataResourceResult<Boolean> {
        return if (params != null) {
            repository.deleteIncome(params.id)
        } else {
            DataResourceResult.Failure(IllegalArgumentException("삭제할 수입 항목의 ID가 null이거나 잘못된 값입니다."))
        }
    }

    data class Params(
        val id: Int
    )
}