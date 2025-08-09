package com.paykidscompose.common.repository

import com.paykidscompose.common.model.allowance.AllowanceChartAmountModel
import com.paykidscompose.common.model.allowance.AllowanceChartCategoryModel
import com.paykidscompose.common.model.allowance.AllowanceChartModel
import com.paykidscompose.common.result.DataResourceResult
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface IncomeAllowanceRepository {
    suspend fun deleteIncome(id: Long): DataResourceResult<Boolean>
    suspend fun saveIncome(request: AllowanceChartModel): DataResourceResult<Boolean>
    suspend fun replaceIncome(request: AllowanceChartModel): DataResourceResult<Boolean>
    suspend fun getIncomeMonthTotalAmount(year: Int, month: Int): DataResourceResult<Int>
    fun getIncomeMonthDailyAmount(
        year: Int,
        month: Int
    ): Flow<DataResourceResult<List<AllowanceChartAmountModel>>>

    fun getIncomeMonthCategory(
        year: Int,
        month: Int,
        category: String
    ): Flow<DataResourceResult<List<AllowanceChartModel>>>

    fun getIncomeMonthAllCategory(
        year: Int,
        month: Int
    ): Flow<DataResourceResult<List<AllowanceChartCategoryModel>>>

    fun getIncomeDay(
        localDate: LocalDate
    ): Flow<DataResourceResult<List<AllowanceChartModel>>>

}