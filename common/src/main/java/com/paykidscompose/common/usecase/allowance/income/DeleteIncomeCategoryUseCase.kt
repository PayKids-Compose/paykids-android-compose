package com.paykidscompose.common.usecase.allowance.income

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.repository.IncomeCategoryRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteIncomeCategoryUseCase @Inject constructor(
    private val repository: IncomeCategoryRepository
) : SuspendUseCase<DeleteIncomeCategoryUseCase.Params, DataResourceResult<Boolean>>() {


    override suspend fun execute(params: Params?): DataResourceResult<Boolean> {
        return if (params != null) {
            repository.deleteIncomeCategory(params.category)
        } else {
            DataResourceResult.Failure(PayKidsException.ToastException(code = -1,"삭제할 수입 카테고리를 제대로 입력하세요."))
        }
    }

    data class Params(
        val category: String
    )
}