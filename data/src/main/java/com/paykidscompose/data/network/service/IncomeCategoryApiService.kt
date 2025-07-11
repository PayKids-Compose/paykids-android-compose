package com.paykidscompose.data.network.service

import com.paykidscompose.data.model.BaseResponse
import com.paykidscompose.data.model.allowance.CategoryDTO
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IncomeCategoryApiService {

    @DELETE("/income/category/delete-category")
    suspend fun deleteIncomeCategory(
        @Query("category") category: String
    ): BaseResponse<Boolean>

    @GET("/income/category/category-list")
    suspend fun getIncomeCategoryList(): BaseResponse<List<CategoryDTO>>

    @POST("/income/category/save-category")
    suspend fun saveIncomeCategory(
        @Query("category") category: String
    ): BaseResponse<Boolean>
}