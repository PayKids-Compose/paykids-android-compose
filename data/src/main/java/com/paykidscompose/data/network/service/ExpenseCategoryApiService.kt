package com.paykidscompose.data.network.service

import com.paykidscompose.data.model.BaseResponse
import com.paykidscompose.data.model.allowance.CategoryDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ExpenseCategoryApiService {
    @DELETE("/expense/category/delete-category")
    suspend fun deleteExpenseCategory(
        @Query("category") category: String
    ): BaseResponse<Boolean>

    @GET ("/expense/category/category-list")
    suspend fun getExpenseCategoryList(): BaseResponse<List<CategoryDTO>>

    @POST("/expense/category/save-category")
    suspend fun saveExpenseCategory(
        @Query("category") category: String
    ): BaseResponse<Boolean>
}