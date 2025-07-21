package com.paykidscompose.common.repositories

import com.paykidscompose.common.model.ChatResponseModel
import com.paykidscompose.common.result.DataResourceResult
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getChatCount(): DataResourceResult<Int>
    fun getChatResponse(prompt: String): Flow<DataResourceResult<ChatResponseModel>>
}