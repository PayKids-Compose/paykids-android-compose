package com.paykidscompose.data.repository

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.model.study.ChatResponseModel
import com.paykidscompose.common.repositories.ChatRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.data.mapper.study.ChatResponseMapper
import com.paykidscompose.data.network.service.ChatApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChatRepositoryImpl(private val chatApiService: ChatApiService) :
    ChatRepository {
    override suspend fun getChatCount(): DataResourceResult<Int> {
        return runCatching {
            chatApiService.getChatCount()
        }.fold(
            onSuccess = { DataResourceResult.Success(it) },
            onFailure = { DataResourceResult.Failure(PayKidsException.ToastException(code = -1, message = it.message ?: "")) }
        )
    }

    override fun getChatResponse(prompt: String): Flow<DataResourceResult<ChatResponseModel>> =
        flow {
            emit(DataResourceResult.Loading)
            runCatching {
                chatApiService.getChatResponse(prompt)
            }.fold(
                onSuccess = { emit(DataResourceResult.Success(ChatResponseMapper.mapToModel(it))) },
                onFailure = { emit(DataResourceResult.Failure(PayKidsException.ToastException(code = -1, message = it.message ?: ""))) }
            )
        }
}