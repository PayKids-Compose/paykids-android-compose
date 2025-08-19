package com.paykidscompose.common.usecase.quiz

import com.paykidscompose.common.repository.QuizRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetStageCountUseCase @Inject constructor(
    private val quizRepository: QuizRepository
) : SuspendUseCase<Unit, DataResourceResult<Int>>() {
    override suspend fun execute(params: Unit?): DataResourceResult<Int> {
        return quizRepository.getStageCount()
    }
}