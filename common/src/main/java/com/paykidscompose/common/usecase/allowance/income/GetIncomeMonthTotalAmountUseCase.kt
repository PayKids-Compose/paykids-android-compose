package com.paykidscompose.common.usecase.allowance.income

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.repository.IncomeAllowanceRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetIncomeMonthTotalAmountUseCase @Inject constructor(
    private val repository: IncomeAllowanceRepository
) : SuspendUseCase<GetIncomeMonthTotalAmountUseCase.Params, DataResourceResult<Int>>() {

    override suspend fun execute(params: Params?): DataResourceResult<Int> {
        return if (params != null) {
            repository.getIncomeMonthTotalAmount(params.year, params.month)
        } else {
            DataResourceResult.Failure(PayKidsException.ToastException(code = -1,"조회할 연도와 월을 제대로 입력해주세요."))
        }
    }

    data class Params(
        val year: Int,
        val month: Int
    )
}