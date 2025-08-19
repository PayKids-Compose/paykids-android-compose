package com.paykidscompose.common.usecase.quiz

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.repository.QuizRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCheckAnswerUseCase @Inject constructor(
    private val quizRepository: QuizRepository
): SuspendUseCase<GetCheckAnswerUseCase.Params, DataResourceResult<Boolean>>() {
    override suspend fun execute(params: Params?): DataResourceResult<Boolean> {
        return if (params != null) {
            quizRepository.getCheckAnswer(params.stage, params.number, params.answer)
        } else {
            DataResourceResult.Failure(PayKidsException.ToastException(code = -1,""))
        }
    }

    data class Params(
        val stage: Int,
        val number: Int,
        val answer: String
    )
}