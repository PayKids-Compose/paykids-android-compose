package com.paykidscompose.common.usecase.allowance.income

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.repositories.IncomeCategoryRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase

class SaveIncomeCategoryUseCase(
    private val repository: IncomeCategoryRepository
) : SuspendUseCase<SaveIncomeCategoryUseCase.Params, DataResourceResult<Boolean>>() {

    override suspend fun execute(params: Params?): DataResourceResult<Boolean> {
        return if (params != null) {
            repository.saveIncomeCategory(params.category)
        } else {
            DataResourceResult.Failure(PayKidsException.ToastException(code = -1,"저장할 수입 카테고리를 제대로 입력하세요."))
        }
    }

    data class Params(
        val category: String
    )
}