package com.paykidscompose.common.usecase.quiz

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.model.QuizClearedModel
import com.paykidscompose.common.repositories.QuizRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase

class GetCheckStageUseCase(
    private val quizRepository: QuizRepository
) : SuspendUseCase<GetCheckStageUseCase.Params, DataResourceResult<QuizClearedModel>>() {
    override suspend fun execute(params: Params?): DataResourceResult<QuizClearedModel> {
        return if (params != null) {
            quizRepository.getCheckStage(params.stage)
        } else {
            DataResourceResult.Failure(PayKidsException.ToastException(code = -1,""))
        }
    }

    data class Params(
        val stage: Int
    )
}