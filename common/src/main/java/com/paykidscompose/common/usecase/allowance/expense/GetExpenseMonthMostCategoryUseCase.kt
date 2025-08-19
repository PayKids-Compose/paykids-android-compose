package com.paykidscompose.common.usecase.allowance.expense

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.model.allowance.AllowanceChartCategoryModel
import com.paykidscompose.common.repository.ExpenseAllowanceRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetExpenseMonthMostCategoryUseCase @Inject constructor(
    private val repository: ExpenseAllowanceRepository
) : FlowUseCase<GetExpenseMonthMostCategoryUseCase.Params, DataResourceResult<List<AllowanceChartCategoryModel>>>() {

    override fun execute(params: Params?): Flow<DataResourceResult<List<AllowanceChartCategoryModel>>> {
        return if (params != null) {
            repository.getExpenseMonthMostCategory(params.year, params.month)
        } else {
            flowOf(DataResourceResult.Failure(PayKidsException.ToastException(code = -1,"가장 많이 소비한 항목을 보려면 연도와 월을 선택해주세요.")))
        }
    }

    data class Params(
        val year: Int,
        val month: Int
    )
}