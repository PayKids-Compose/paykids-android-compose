package com.paykidscompose.data.repositories

import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.data.model.quiz.QuizClearedDTO
import com.paykidscompose.data.model.quiz.QuizDTO
import com.paykidscompose.data.network.NetworkModule
import com.paykidscompose.data.network.service.QuizApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class QuizRepositoryImpl(private val quizApiService: QuizApiService = NetworkModule.provideQuizApiService()) {
    suspend fun getStageToGo(): DataResourceResult<Int> {
        return runCatching {
            quizApiService.getStageToGo()
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    suspend fun getStageName(stage: Int): DataResourceResult<String> {
        return runCatching {
            quizApiService.getStageName(stage)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    suspend fun getStagCount(): DataResourceResult<Int> {
        return runCatching {
            quizApiService.getStageCount()
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    suspend fun getCheckStage(stage: Int): DataResourceResult<QuizClearedDTO> {
        return runCatching {
            quizApiService.getCheckStage(stage)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    suspend fun getCheckAnswer(stage: Int, number: Int, answer: String): DataResourceResult<Boolean> {
        return runCatching {
            quizApiService.getCheckAnswer(stage, number, answer)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    fun getQuiz(stage: Int, number: Int): Flow<DataResourceResult<QuizDTO>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            quizApiService.getQuiz(stage, number)
        }.fold(
            onSuccess = { emit(DataResourceResult.Success(it.data)) },
            onFailure = { emit(DataResourceResult.Failure(it)) }
        )
    }

    fun getIncorrectQuizList(stage: Int): Flow<DataResourceResult<List<Int>>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            quizApiService.getIncorrectQuizList(stage)
        }.fold(
            onSuccess = { emit(DataResourceResult.Success(it.data)) },
            onFailure = { emit(DataResourceResult.Failure(it)) }
        )
    }

}