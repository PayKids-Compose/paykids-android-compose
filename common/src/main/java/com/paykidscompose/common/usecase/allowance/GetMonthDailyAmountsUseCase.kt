package com.paykidscompose.common.usecase.allowance

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.repository.ExpenseAllowanceRepository
import com.paykidscompose.common.repository.IncomeAllowanceRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate

class GetMonthDailyAmountsUseCase(
    private val expenseRepository: ExpenseAllowanceRepository,
    private val incomeRepository: IncomeAllowanceRepository
) : FlowUseCase<GetMonthDailyAmountsUseCase.Params, DataResourceResult<Map<LocalDate, Pair<Int, Int>>>>() {

    data class Params(val year: Int, val month: Int)

    override fun execute(params: Params?): Flow<DataResourceResult<Map<LocalDate, Pair<Int, Int>>>> {
        return if (params != null) {
            combine(
                expenseRepository.getExpenseMonthDailyAmount(params.year, params.month),
                incomeRepository.getIncomeMonthDailyAmount(params.year, params.month)
            ) { expenseResult, incomeResult ->
                when {
                    expenseResult is DataResourceResult.Success && incomeResult is DataResourceResult.Success -> {
                        val expenseMap = expenseResult.data.groupBy { it.date }
                            .mapValues { (_, chartAmountModel) ->
                                chartAmountModel.sumOf { it.amount }
                            }

                        val incomeMap = incomeResult.data.groupBy { it.date }
                            .mapValues { (_, chartAmountModel) ->
                                chartAmountModel.sumOf { it.amount }
                            }

                        val allDates = (expenseMap.keys + incomeMap.keys).toSet()

                        val resultMap = allDates.associateWith { date ->
                            Pair(expenseMap[date] ?: 0, incomeMap[date] ?: 0)
                        }

                        DataResourceResult.Success(resultMap)
                    }

                    expenseResult is DataResourceResult.Failure -> {
                        DataResourceResult.Failure(expenseResult.exception)
                    }

                    incomeResult is DataResourceResult.Failure -> {
                        DataResourceResult.Failure(incomeResult.exception)
                    }

                    else -> DataResourceResult.Failure(PayKidsException.ToastException(code = -1, message = "알 수 없는 에러 발생"))
                }
            }
        } else {
            flowOf(
                DataResourceResult.Failure(
                    PayKidsException.ToastException(
                        code = -1,
                        message = "유효하지 않은 달 입니다."
                    )
                )
            )
        }
    }
}