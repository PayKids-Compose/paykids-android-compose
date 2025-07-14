package com.paykidscompose.data.network.service

import com.paykidscompose.data.model.study.ChatResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ChatApiService {
    @GET("/gpt/number")
    suspend fun getChatCount(): Int

    @GET("/gpt/chat")
    suspend fun getChatResponse(
        @Query("prompt") prompt: String
    ): ChatResponseDTO
}