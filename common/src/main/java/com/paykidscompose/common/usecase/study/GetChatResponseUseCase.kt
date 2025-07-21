package com.paykidscompose.common.usecase.study

import com.paykidscompose.common.model.ChatResponseModel
import com.paykidscompose.common.repositories.ChatRepository
import com.paykidscompose.common.result.DataResourceResult
import kotlinx.coroutines.flow.Flow

class GetChatResponseUseCase(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(prompt: String): Flow<DataResourceResult<ChatResponseModel>> {
        return chatRepository.getChatResponse(prompt)
    }
}