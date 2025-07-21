package com.paykidscompose.common.usecase.quiz

import com.paykidscompose.common.model.QuizClearedModel
import com.paykidscompose.common.repositories.QuizRepository
import com.paykidscompose.common.result.DataResourceResult

class GetCheckStageUseCase(
    private val quizRepository: QuizRepository
) {
    suspend operator fun invoke(stage: Int): DataResourceResult<QuizClearedModel> {
        return quizRepository.getCheckStage(stage)
    }
}