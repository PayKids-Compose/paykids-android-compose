package com.paykidscompose.common.usecase.study

import com.paykidscompose.common.repositories.ChatRepository
import com.paykidscompose.common.result.DataResourceResult

class GetChatCountUseCase(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(): DataResourceResult<Int> {
        return chatRepository.getChatCount()
    }
}