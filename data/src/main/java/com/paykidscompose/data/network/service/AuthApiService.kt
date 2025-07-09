package com.paykidscompose.data.network.service

import com.paykidscompose.data.model.BaseResponse
import com.paykidscompose.data.model.user.LoginDTO
import retrofit2.http.POST

interface AuthApiService {
    @POST("/auth/refresh")
    suspend fun fetchRefreshToken(refreshToken: String): BaseResponse<LoginDTO>

    @POST("/auth/login")
    suspend fun fetchLoginToken(idToken: String): BaseResponse<LoginDTO>
}