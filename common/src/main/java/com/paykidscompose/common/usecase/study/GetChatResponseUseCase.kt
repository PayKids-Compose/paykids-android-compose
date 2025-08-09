package com.paykidscompose.common.usecase.study

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.model.study.ChatResponseModel
import com.paykidscompose.common.repository.ChatRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class GetChatResponseUseCase(
    private val chatRepository: ChatRepository
) : FlowUseCase<GetChatResponseUseCase.Params, DataResourceResult<ChatResponseModel>>() {
    override fun execute(params: Params?): Flow<DataResourceResult<ChatResponseModel>> = flow {
        if (params == null) {
            emit(
                DataResourceResult.Failure(
                    PayKidsException.ToastException(
                        code = -1,
                        message = "요청 파라미터가 없습니다."
                    )
                )
            )
            return@flow
        }

        when (val countResult = chatRepository.getChatCount()) {
            is DataResourceResult.Success -> {
                val count = countResult.data
                if (count <= 0) { // 남은 요청 횟수가 0이 되면 GPT 요청 불가능
                    emit(
                        DataResourceResult.Failure(
                            PayKidsException.ToastException(
                                code = -1,
                                message = "더 이상 요청할 수 없습니다."
                            )
                        )
                    )
                } else {
                    emitAll(chatRepository.getChatResponse(params.prompt))
                }
            }

            is DataResourceResult.Failure -> {
                emit(
                    DataResourceResult.Failure(
                        PayKidsException.ToastException(
                            code = -1,
                            message = "채팅 카운트를 불러오는 데 실패했습니다."
                        )
                    )
                )
            }

            DataResourceResult.DummyConstructor -> {}
            DataResourceResult.Loading -> {}
        }
    }

    data class Params(
        val prompt: String
    )
}