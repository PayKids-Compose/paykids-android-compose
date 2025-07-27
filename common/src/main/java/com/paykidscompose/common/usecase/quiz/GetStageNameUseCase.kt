package com.paykidscompose.common.usecase.quiz

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.repositories.QuizRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase

class GetStageNameUseCase(
    private val quizRepository: QuizRepository
) : SuspendUseCase<GetStageNameUseCase.Params, DataResourceResult<String>>() {
    override suspend fun execute(params: Params?): DataResourceResult<String> {
        return if (params != null) {
            quizRepository.getStageName(params.stage)
        } else {
            DataResourceResult.Failure(PayKidsException.ToastException(code = -1,""))
        }
    }

    data class Params(
        val stage: Int
    )
}