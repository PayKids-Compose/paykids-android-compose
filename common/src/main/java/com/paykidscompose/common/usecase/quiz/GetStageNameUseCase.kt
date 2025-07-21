package com.paykidscompose.common.usecase.quiz

import com.paykidscompose.common.repositories.QuizRepository
import com.paykidscompose.common.result.DataResourceResult

class GetStageNameUseCase(
    private val quizRepository: QuizRepository
) {
    suspend operator fun invoke(stage: Int): DataResourceResult<String> {
        return quizRepository.getStageName(stage)
    }
}