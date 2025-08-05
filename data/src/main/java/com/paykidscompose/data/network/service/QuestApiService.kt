package com.paykidscompose.data.network.service

import com.paykidscompose.data.model.BaseResponse
import com.paykidscompose.data.model.quest.QuestDTO
import retrofit2.http.GET

interface QuestApiService {
    @GET("/Quest/list")
    suspend fun getQuests(): BaseResponse<List<QuestDTO>>
}