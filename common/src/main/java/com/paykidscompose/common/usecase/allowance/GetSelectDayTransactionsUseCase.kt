package com.paykidscompose.common.usecase.allowance

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.model.allowance.AllowanceChartModel
import com.paykidscompose.common.repository.ExpenseAllowanceRepository
import com.paykidscompose.common.repository.IncomeAllowanceRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSelectDayTransactionsUseCase @Inject constructor(
    private val expenseRepository: ExpenseAllowanceRepository,
    private val incomeRepository: IncomeAllowanceRepository
) : FlowUseCase<GetSelectDayTransactionsUseCase.Params, DataResourceResult<List<AllowanceChartModel>>>() {

    override fun execute(params: Params?): Flow<DataResourceResult<List<AllowanceChartModel>>> {
        return if (params != null) {
            combine(
                expenseRepository.getExpenseDay(params.localDate),
                incomeRepository.getIncomeDay(params.localDate)
            ) { expenseResult, incomeResult ->
                when {
                    expenseResult is DataResourceResult.Success && incomeResult is DataResourceResult.Success -> {
                        DataResourceResult.Success(
                            (expenseResult.data + incomeResult.data)
                                .sortedByDescending { it.date })
                    }

                    expenseResult is DataResourceResult.Failure -> {
                        DataResourceResult.Failure(expenseResult.exception)
                    }

                    incomeResult is DataResourceResult.Failure -> {
                        DataResourceResult.Failure(incomeResult.exception)
                    }

                    expenseResult is DataResourceResult.Loading || incomeResult is DataResourceResult.Loading -> {
                        DataResourceResult.Loading
                    }

                    expenseResult is DataResourceResult.DummyConstructor || incomeResult is DataResourceResult.DummyConstructor -> {
                        DataResourceResult.DummyConstructor
                    }

                    else -> {
                        DataResourceResult.Success(emptyList())
                    }
                }
            }
        } else {
            flowOf(
                DataResourceResult.Failure(
                    PayKidsException.ToastException(
                        code = -1,
                        message = "내역을 확인 하고 싶은 날짜를 제대로 선택하세요."
                    )
                )
            )
        }
    }

    data class Params(
        val localDate: LocalDate
    )
}