package com.paykidscompose.common.usecase.quiz

import com.paykidscompose.common.repositories.QuizRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase

class GetCheckAnswerUseCase(
    private val quizRepository: QuizRepository
): SuspendUseCase<GetCheckAnswerUseCase.Params, DataResourceResult<Boolean>>() {
    override suspend fun execute(params: Params?): DataResourceResult<Boolean> {
        return if (params != null) {
            quizRepository.getCheckAnswer(params.stage, params.number, params.answer)
        } else {
            DataResourceResult.Failure(IllegalArgumentException(""))
        }
    }

    data class Params(
        val stage: Int,
        val number: Int,
        val answer: String
    )
}