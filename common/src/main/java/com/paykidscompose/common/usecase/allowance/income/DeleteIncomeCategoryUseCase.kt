package com.paykidscompose.common.usecase.allowance.income

import com.paykidscompose.common.repositories.IncomeCategoryRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase

class DeleteIncomeCategoryUseCase(
    private val repository: IncomeCategoryRepository
) : SuspendUseCase<DeleteIncomeCategoryUseCase.Params, DataResourceResult<Boolean>>() {


    override suspend fun execute(params: Params?): DataResourceResult<Boolean> {
        return if (params != null) {
            repository.deleteIncomeCategory(params.category)
        } else {
            DataResourceResult.Failure(IllegalArgumentException("삭제할 수입 카테고리를 제대로 입력하세요."))
        }
    }

    data class Params(
        val category: String
    )
}