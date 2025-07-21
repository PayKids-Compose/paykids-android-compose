package com.paykidscompose.common.usecase.quiz

import com.paykidscompose.common.repositories.QuizRepository
import com.paykidscompose.common.result.DataResourceResult

class GetCheckAnswerUseCase(
    private val quizRepository: QuizRepository
) {
    suspend operator fun invoke(stage: Int, number: Int, answer: String): DataResourceResult<Boolean> {
        return quizRepository.getCheckAnswer(stage, number, answer)
    }
}