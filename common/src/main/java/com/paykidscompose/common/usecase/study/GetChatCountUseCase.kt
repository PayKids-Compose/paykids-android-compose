package com.paykidscompose.common.usecase.study

import com.paykidscompose.common.repository.ChatRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase

class GetChatCountUseCase(
    private val chatRepository: ChatRepository
) : SuspendUseCase<Unit, DataResourceResult<Int>>() {
    override suspend fun execute(params: Unit?): DataResourceResult<Int> {
        return chatRepository.getChatCount()
    }
}