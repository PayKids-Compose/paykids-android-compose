package com.paykidscompose.common.usecase.allowance.expense

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.model.allowance.AllowanceChartModel
import com.paykidscompose.common.repository.ExpenseAllowanceRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetExpenseDayUseCase @Inject constructor(
    private val repository: ExpenseAllowanceRepository
) : FlowUseCase<GetExpenseDayUseCase.Params, DataResourceResult<List<AllowanceChartModel>>>() {

    override fun execute(params: Params?): Flow<DataResourceResult<List<AllowanceChartModel>>> {
        return if (params != null) {
            repository.getExpenseDay(params.localDate)
        } else {
            flowOf(DataResourceResult.Failure(PayKidsException.ToastException(code = -1,"일별 소비 데이터를 조회하려면 날짜 정보를 정확히 입력해야 합니다.")))
        }
    }

    data class Params(
        val localDate: LocalDate
    )
}