package com.paykidscompose.data.network.service

import com.paykidscompose.data.model.BaseResponse
import com.paykidscompose.data.model.user.UserDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApiService {
    @DELETE("/user/delete")
    suspend fun deleteUser(): BaseResponse<String>

    @GET("/user/info")
    suspend fun getUser(): BaseResponse<UserDTO>

    @POST("/user/profile-image/change")
    suspend fun replaceProfileImage(
        @Body file: String
    ): BaseResponse<String>

    @POST("/user/nickname/save")
    suspend fun saveNickname(
        @Query("nickname") nickname: String
    ): BaseResponse<String>

    @POST("/user/nickname/change")
    suspend fun replaceNickname(
        @Query("newNickname") newNickname: String
    ): BaseResponse<String>
}