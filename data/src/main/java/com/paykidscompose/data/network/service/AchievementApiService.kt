package com.paykidscompose.data.network.service

import com.paykidscompose.data.model.BaseResponse
import com.paykidscompose.data.model.quest_achivement.AchievementDTO
import retrofit2.http.GET

interface AchievementApiService {
    @GET("/Achievement/list")
    suspend fun getAchievements(): BaseResponse<List<AchievementDTO>>
}