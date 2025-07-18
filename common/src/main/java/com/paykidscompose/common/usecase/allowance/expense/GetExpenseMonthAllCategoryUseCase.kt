package com.paykidscompose.common.usecase.allowance.expense

import com.paykidscompose.common.model.allowance.AllowanceChartCategoryModel
import com.paykidscompose.common.repositories.ExpenseAllowanceRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetExpenseMonthAllCategoryUseCase(
    private val repository: ExpenseAllowanceRepository
) : FlowUseCase<GetExpenseMonthAllCategoryUseCase.Params, DataResourceResult<List<AllowanceChartCategoryModel>>>() {

    override fun execute(params: Params?): Flow<DataResourceResult<List<AllowanceChartCategoryModel>>> {
        return if (params != null) {
            repository.getExpenseMonthAllCategory(params.year, params.month)
        } else {
            flowOf(DataResourceResult.Failure(IllegalArgumentException("월별 카테고리를 조회하려면 연도와 월 정보를 정확히 입력해야 합니다.")))
        }
    }

    data class Params(
        val year: Int,
        val month: Int
    )
}