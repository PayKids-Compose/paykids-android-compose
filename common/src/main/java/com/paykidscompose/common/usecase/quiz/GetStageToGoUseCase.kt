package com.paykidscompose.common.usecase.quiz

import com.paykidscompose.common.repositories.QuizRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase

class GetStageToGoUseCase(
    private val quizRepository: QuizRepository
) : SuspendUseCase<Unit, DataResourceResult<Int>>() {
    override suspend fun execute(params: Unit?): DataResourceResult<Int> {
        return quizRepository.getStageToGo()
    }
}