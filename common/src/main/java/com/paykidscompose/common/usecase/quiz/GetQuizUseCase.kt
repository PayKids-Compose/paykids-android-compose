package com.paykidscompose.common.usecase.quiz

import com.paykidscompose.common.model.QuizModel
import com.paykidscompose.common.repositories.QuizRepository
import com.paykidscompose.common.result.DataResourceResult
import kotlinx.coroutines.flow.Flow

class GetQuizUseCase(
    private val quizRepository: QuizRepository
) {
    operator fun invoke(stage: Int, number: Int): Flow<DataResourceResult<QuizModel>> {
        return quizRepository.getQuiz(stage, number)
    }
}