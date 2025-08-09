package com.paykidscompose.common.usecase.allowance.income

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.model.allowance.AllowanceChartModel
import com.paykidscompose.common.repository.IncomeAllowanceRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetIncomeMonthCategoryUseCase(
    private val repository: IncomeAllowanceRepository
) : FlowUseCase<GetIncomeMonthCategoryUseCase.Params, DataResourceResult<List<AllowanceChartModel>>>() {

    override fun execute(params: Params?): Flow<DataResourceResult<List<AllowanceChartModel>>> {
        return if (params != null) {
            repository.getIncomeMonthCategory(params.year, params.month, params.category)
        } else {
            flowOf(DataResourceResult.Failure(PayKidsException.ToastException(code = -1,"조회할 날짜와 카테고리를 제대로 입력해주세요.")))
        }
    }

    data class Params(
        val year: Int,
        val month: Int,
        val category: String
    )
}