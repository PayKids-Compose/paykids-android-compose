package com.paykidscompose.common.usecase.quiz

import com.paykidscompose.common.repository.QuizRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase

class GetStageCountUseCase(
    private val quizRepository: QuizRepository
) : SuspendUseCase<Unit, DataResourceResult<Int>>() {
    override suspend fun execute(params: Unit?): DataResourceResult<Int> {
        return quizRepository.getStageCount()
    }
}