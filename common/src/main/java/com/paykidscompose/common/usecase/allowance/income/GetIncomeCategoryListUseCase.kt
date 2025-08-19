package com.paykidscompose.common.usecase.allowance.income

import com.paykidscompose.common.model.allowance.CategoryModel
import com.paykidscompose.common.repository.IncomeCategoryRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetIncomeCategoryListUseCase @Inject constructor(
    private val repository: IncomeCategoryRepository
) : FlowUseCase<Unit, DataResourceResult<List<CategoryModel>>>() {

    override fun execute(params: Unit?): Flow<DataResourceResult<List<CategoryModel>>> =
        repository.getIncomeCategoryList()
}