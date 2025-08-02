package com.paykidscompose.common.usecase.quiz

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.model.quiz.QuizModel
import com.paykidscompose.common.repositories.QuizRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetQuizUseCase(
    private val quizRepository: QuizRepository
) : FlowUseCase<GetQuizUseCase.Params, DataResourceResult<QuizModel>>() {
    override fun execute(params: Params?): Flow<DataResourceResult<QuizModel>> {
        return if (params != null) {
            quizRepository.getQuiz(params.stage, params.number)
        } else {
            flowOf(DataResourceResult.Failure(PayKidsException.ToastException(code = -1,"")))
        }
    }

    data class Params(
        val stage: Int,
        val number: Int
    )
}