package com.paykidscompose.common.usecase.allowance.income

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.repository.IncomeCategoryRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveIncomeCategoryUseCase @Inject constructor(
    private val repository: IncomeCategoryRepository
) : SuspendUseCase<SaveIncomeCategoryUseCase.Params, DataResourceResult<Boolean>>() {

    override suspend fun execute(params: Params?): DataResourceResult<Boolean> {
        return if (params != null && params.category.length >= 1 && params.category.length <= 5) {
            repository.saveIncomeCategory(params.category)
        } else {
            DataResourceResult.Failure(PayKidsException.ToastException(code = -1,"카테고리는 1자 이상 5자 이하로 입력해주세요!"))
        }
    }

    data class Params(
        val category: String
    )
}