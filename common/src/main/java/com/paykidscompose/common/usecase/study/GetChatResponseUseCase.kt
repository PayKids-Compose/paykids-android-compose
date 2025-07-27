package com.paykidscompose.common.usecase.study

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.model.ChatResponseModel
import com.paykidscompose.common.repositories.ChatRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetChatResponseUseCase(
    private val chatRepository: ChatRepository
) : FlowUseCase<GetChatResponseUseCase.Params, DataResourceResult<ChatResponseModel>>() {
    override fun execute(params: Params?): Flow<DataResourceResult<ChatResponseModel>> {
        return if (params != null) {
            chatRepository.getChatResponse(params.prompt)
        } else {
            flowOf(DataResourceResult.Failure(PayKidsException.ToastException(code = -1,"")))
        }
    }

    data class Params(
        val prompt: String
    )
}