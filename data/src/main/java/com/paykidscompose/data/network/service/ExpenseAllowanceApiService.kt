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

interface ExpenseAllowanceApiService {
    @DELETE("/expense/allowance/delete")
    suspend fun deleteExpense(@Query("id") id: Long): BaseResponse<Boolean>

    @GET("/expense/allowance/month-total-amount")
    suspend fun getExpenseMonthTotalAmount(
        @Query("year") year: Int,
        @Query("month") month: Int
    ): BaseResponse<Int>

    @GET("/expense/allowance/month-most-category")
    suspend fun getExpenseMonthMostCategory(
        @Query("year") year: Int,
        @Query("month") month: Int
    ): BaseResponse<List<AllowanceChartCategoryDTO>>

    @GET("/expense/allowance/month-daily-amount")
    suspend fun getExpenseMonthDailyAmount(
        @Query("year") year: Int,
        @Query("month") month: Int
    ): BaseResponse<List<AllowanceChartAmountDTO>>

    @GET("/expense/allowance/month-category")
    suspend fun getExpenseMonthCategory(
        @Query("year") year: Int,
        @Query("month") month: Int,
        @Query("category") category: String
    ): BaseResponse<List<AllowanceChartDTO>>

    @GET("/expense/allowance/month-all-category")
    suspend fun getExpenseMonthAllCategory(
        @Query("year") year: Int,
        @Query("month") month: Int
    ): BaseResponse<List<AllowanceChartCategoryDTO>>

    @GET("/expense/allowance/day")
    suspend fun getExpenseDay(
        @Query("localDate") localDate: String
    ): BaseResponse<List<AllowanceChartDTO>>

    @POST("/expense/allowance/save")
    suspend fun saveExpense(
        @Body request: AllowanceChartDTO
    ): BaseResponse<Boolean>

    @POST("/expense/allowance/replace")
    suspend fun replaceExpense(
        @Body request: AllowanceChartDTO
    ): BaseResponse<Boolean>
}