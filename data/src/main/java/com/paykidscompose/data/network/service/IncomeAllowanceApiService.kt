package com.paykidscompose.data.network.service

import com.paykidscompose.data.model.BaseResponse
import com.paykidscompose.data.model.allowance.AllowanceChartAmountDTO
import com.paykidscompose.data.model.allowance.AllowanceChartCategoryDTO
import com.paykidscompose.data.model.allowance.AllowanceChartDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IncomeAllowanceApiService {
    @DELETE("/income/allowance/delete")
    suspend fun deleteIncome(
        @Query("id") id: Int
    ): BaseResponse<Boolean>

    @GET("/income/allowance/month-total-amount")
    suspend fun getIncomeMonthTotalAmount(
        @Query("year") year: Int,
        @Query("month") month: Int
    ): BaseResponse<Int>

    @GET("/income/allowance/month-daily-amount")
    suspend fun getIncomeMonthDailyAmount(
        @Query("year") year: Int,
        @Query("month") month: Int
    ): BaseResponse<List<AllowanceChartAmountDTO>>

    @GET("/income/allowance/month-category")
    suspend fun getIncomeMonthCategory(
        @Query("year") year: Int,
        @Query("month") month: Int,
        @Query("category") category: String
    ): BaseResponse<List<AllowanceChartDTO>>

    @GET("/income/allowance/month-all-category")
    suspend fun getIncomeMonthAllCategory(
        @Query("year") year: Int,
        @Query("month") month: Int
    ): BaseResponse<List<AllowanceChartCategoryDTO>>

    @GET("/income/allowance/day")
    suspend fun getIncomeDay(
        @Query("localDate") localDate: String
    ): BaseResponse<List<AllowanceChartDTO>>

    @POST("/income/allowance/save")
    suspend fun saveIncome(
        @Body request: AllowanceChartDTO
    ): BaseResponse<Boolean>

    @POST("/income/allowance/replace")
    suspend fun replaceIncome(
        @Body request: AllowanceChartDTO
    ): BaseResponse<Boolean>
}