package com.paykidscompose.data.repositories

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.model.QuizClearedModel
import com.paykidscompose.common.model.QuizModel
import com.paykidscompose.common.repositories.QuizRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.data.mapper.quiz.QuizClearedMapper
import com.paykidscompose.data.mapper.quiz.QuizMapper
import com.paykidscompose.data.network.NetworkModule
import com.paykidscompose.data.network.service.QuizApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class QuizRepositoryImpl(private val quizApiService: QuizApiService):
    QuizRepository {
    override suspend fun getStageToGo(): DataResourceResult<Int> {
        return runCatching {
            quizApiService.getStageToGo()
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(PayKidsException.ToastException(code = -1, message = it.message ?: "")) }
        )
    }

    override suspend fun getStageName(stage: Int): DataResourceResult<String> {
        return runCatching {
            quizApiService.getStageName(stage)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(PayKidsException.ToastException(code = -1, message = it.message ?: "")) }
        )
    }

    override suspend fun getStageCount(): DataResourceResult<Int> {
        return runCatching {
            quizApiService.getStageCount()
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(PayKidsException.ToastException(code = -1, message = it.message ?: "")) }
        )
    }

    override suspend fun getCheckStage(stage: Int): DataResourceResult<QuizClearedModel> {
        return runCatching {
            quizApiService.getCheckStage(stage)
        }.fold(
            onSuccess = { DataResourceResult.Success(QuizClearedMapper.mapToModel(it.data)) },
            onFailure = { DataResourceResult.Failure(PayKidsException.ToastException(code = -1, message = it.message ?: "")) }
        )
    }

    override suspend fun getCheckAnswer(stage: Int, number: Int, answer: String): DataResourceResult<Boolean> {
        return runCatching {
            quizApiService.getCheckAnswer(stage, number, answer)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(PayKidsException.ToastException(code = -1, message = it.message ?: "")) }
        )
    }

    override fun getQuiz(stage: Int, number: Int): Flow<DataResourceResult<QuizModel>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            quizApiService.getQuiz(stage, number)
        }.fold(
            onSuccess = { emit(DataResourceResult.Success(QuizMapper.mapToModel(it.data))) },
            onFailure = { emit(DataResourceResult.Failure(PayKidsException.ToastException(code = -1, message = it.message ?: ""))) }
        )
    }

    override fun getIncorrectQuizList(stage: Int): Flow<DataResourceResult<List<Int>>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            quizApiService.getIncorrectQuizList(stage)
        }.fold(
            onSuccess = { emit(DataResourceResult.Success(it.data)) },
            onFailure = { emit(DataResourceResult.Failure(PayKidsException.ToastException(code = -1, message = it.message ?: ""))) }
        )
    }

}