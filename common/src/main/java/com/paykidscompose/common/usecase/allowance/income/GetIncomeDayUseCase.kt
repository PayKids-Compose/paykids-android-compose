package com.paykidscompose.common.usecase.allowance.income

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.model.allowance.AllowanceChartModel
import com.paykidscompose.common.repository.IncomeAllowanceRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate

class GetIncomeDayUseCase(
    private val repository: IncomeAllowanceRepository
) : FlowUseCase<GetIncomeDayUseCase.Params, DataResourceResult<List<AllowanceChartModel>>>() {

    override fun execute(params: Params?): Flow<DataResourceResult<List<AllowanceChartModel>>> {
        return if (params != null) {
            repository.getIncomeDay(params.localDate)
        } else {
            flowOf(DataResourceResult.Failure(PayKidsException.ToastException(code = -1,"일별 수입 데이터를 조회하려면 날짜를 제대로 입력해주세요.")))
        }
    }

    data class Params(
        val localDate: LocalDate
    )
}