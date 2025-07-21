package com.paykidscompose.common.usecase.quiz

import com.paykidscompose.common.repositories.QuizRepository
import com.paykidscompose.common.result.DataResourceResult

class GetStageCountUseCase(
    private val quizRepository: QuizRepository
) {
    suspend operator fun invoke(): DataResourceResult<Int> {
        return quizRepository.getStageCount()
    }
}