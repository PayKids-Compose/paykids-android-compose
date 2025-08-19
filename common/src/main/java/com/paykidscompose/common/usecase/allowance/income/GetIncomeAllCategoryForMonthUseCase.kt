package com.paykidscompose.common.usecase.allowance.income

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.model.allowance.AllowanceChartCategoryModel
import com.paykidscompose.common.repository.IncomeAllowanceRepository
import com.paykidscompose.common.repository.IncomeCategoryRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetIncomeAllCategoryForMonthUseCase @Inject constructor(
    private val allowanceRepository: IncomeAllowanceRepository,
    private val categoryRepository: IncomeCategoryRepository
) : FlowUseCase<GetIncomeAllCategoryForMonthUseCase.Params, DataResourceResult<List<AllowanceChartCategoryModel>>>() {
    override fun execute(params: Params?): Flow<DataResourceResult<List<AllowanceChartCategoryModel>>> {
        return if (params != null) {
            combine(
                categoryRepository.getIncomeCategoryList(),
                allowanceRepository.getIncomeMonthAllCategory(params.year, params.month)
            ) { categoryResult, monthResult ->
                when {
                    categoryResult is DataResourceResult.Success && monthResult is DataResourceResult.Success -> {
                        val merge = categoryResult.data.map { category ->
                            val match = monthResult.data.find { it.category == category.title }
                            AllowanceChartCategoryModel(
                                category = category.title,
                                type = category.type,
                                amount = match?.amount ?: 0,
                                percent = match?.percent ?: 0
                            )
                        }

                        DataResourceResult.Success(merge)
                    }

                    categoryResult is DataResourceResult.Failure -> {
                        DataResourceResult.Failure(categoryResult.exception)
                    }

                    monthResult is DataResourceResult.Failure -> {
                        DataResourceResult.Failure(monthResult.exception)
                    }

                    categoryResult is DataResourceResult.Loading || monthResult is DataResourceResult.Loading -> {
                        DataResourceResult.Loading
                    }

                    categoryResult is DataResourceResult.DummyConstructor || monthResult is DataResourceResult.DummyConstructor -> {
                        DataResourceResult.DummyConstructor
                    }

                    else -> {
                        DataResourceResult.Failure(
                            PayKidsException.ToastException(
                                code = -1,
                                message = "알 수 없는 에러 발생"
                            )
                        )
                    }
                }
            }
        } else {
            flowOf(
                DataResourceResult.Failure(
                    PayKidsException.ToastException(
                        code = -1,
                        message = "조회할 달이 유효하지 않습니다."
                    )
                )
            )
        }
    }


    data class Params(
        val year: Int,
        val month: Int
    )
}