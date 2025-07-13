package com.paykidscompose.data.repositories

import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.data.model.study.ChatResponseDTO
import com.paykidscompose.data.network.NetworkModule
import com.paykidscompose.data.network.service.ChatApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChatRepositoryImpl(private val chatApiService: ChatApiService = NetworkModule.provideChatApiService()) {
    suspend fun getChatCount(): DataResourceResult<Int> {
        return runCatching {
            chatApiService.getChatCount()
        }.fold(
            onSuccess = { DataResourceResult.Success(it) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    fun getChatResponse(prompt: String): Flow<DataResourceResult<ChatResponseDTO>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            chatApiService.getChatResponse(prompt)
        }.fold(
            onSuccess = { emit(DataResourceResult.Success(it)) },
            onFailure = { emit(DataResourceResult.Failure(it)) }
        )
    }
}