package com.paykidscompose.common.usecase.quiz

import com.paykidscompose.common.repositories.QuizRepository
import com.paykidscompose.common.result.DataResourceResult
import kotlinx.coroutines.flow.Flow

class GetIncorrectQuizListUseCase(
    private val quizRepository: QuizRepository
) {
    operator fun invoke(stage: Int): Flow<DataResourceResult<List<Int>>> {
        return quizRepository.getIncorrectQuizList(stage)
    }
}