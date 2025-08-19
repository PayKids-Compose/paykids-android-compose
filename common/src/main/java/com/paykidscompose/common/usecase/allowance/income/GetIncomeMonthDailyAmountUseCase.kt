package com.paykidscompose.common.usecase.allowance.income

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.model.allowance.AllowanceChartAmountModel
import com.paykidscompose.common.repository.IncomeAllowanceRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetIncomeMonthDailyAmountUseCase @Inject constructor(
    private val repository: IncomeAllowanceRepository
) : FlowUseCase<GetIncomeMonthDailyAmountUseCase.Params, DataResourceResult<List<AllowanceChartAmountModel>>>() {

    override fun execute(params: Params?): Flow<DataResourceResult<List<AllowanceChartAmountModel>>> {
        return if (params != null) {
            repository.getIncomeMonthDailyAmount(params.year, params.month)
        } else {
            flowOf(DataResourceResult.Failure(PayKidsException.ToastException(code = -1,"일별 수입 내역을 보려면 연도와 월을 선택해주세요.")))
        }
    }

    data class Params(
        val year: Int,
        val month: Int
    )
}