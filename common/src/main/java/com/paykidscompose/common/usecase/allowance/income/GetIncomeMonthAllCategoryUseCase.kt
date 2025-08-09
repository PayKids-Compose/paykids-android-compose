package com.paykidscompose.common.usecase.allowance.income

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.model.allowance.AllowanceChartCategoryModel
import com.paykidscompose.common.repository.IncomeAllowanceRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetIncomeMonthAllCategoryUseCase(
    private val repository: IncomeAllowanceRepository
) : FlowUseCase<GetIncomeMonthAllCategoryUseCase.Params, DataResourceResult<List<AllowanceChartCategoryModel>>>() {

    override fun execute(params: Params?): Flow<DataResourceResult<List<AllowanceChartCategoryModel>>> {
        return if (params != null) {
            repository.getIncomeMonthAllCategory(params.year, params.month)
        } else {
            flowOf(DataResourceResult.Failure(PayKidsException.ToastException(code = -1,"조회할 연도와 월을 제대로 입력해주세요.")))
        }
    }

    data class Params(
        val year: Int,
        val month: Int
    )
}