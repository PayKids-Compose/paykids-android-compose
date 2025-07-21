package com.paykidscompose.common.usecase.quiz

import com.paykidscompose.common.repositories.QuizRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetIncorrectQuizListUseCase(
    private val quizRepository: QuizRepository
) : FlowUseCase<GetIncorrectQuizListUseCase.Params, DataResourceResult<List<Int>>>() {
    override fun execute(params: Params?): Flow<DataResourceResult<List<Int>>> {
        return if (params != null) {
            quizRepository.getIncorrectQuizList(params.stage)
        } else {
            flowOf(DataResourceResult.Failure(IllegalArgumentException("")))
        }
    }

    data class Params(
        val stage: Int
    )
}