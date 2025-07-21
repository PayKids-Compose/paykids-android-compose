package com.paykidscompose.common.repositories

import com.paykidscompose.common.model.QuizClearedModel
import com.paykidscompose.common.model.QuizModel
import com.paykidscompose.common.result.DataResourceResult
import kotlinx.coroutines.flow.Flow

interface QuizRepository {
    suspend fun getStageToGo(): DataResourceResult<Int>
    suspend fun getStageName(stage: Int): DataResourceResult<String>
    suspend fun getStageCount(): DataResourceResult<Int>
    suspend fun getCheckStage(stage: Int): DataResourceResult<QuizClearedModel>
    suspend fun getCheckAnswer(stage: Int, number: Int, answer: String): DataResourceResult<Boolean>
    fun getQuiz(stage: Int, number: Int): Flow<DataResourceResult<QuizModel>>
    fun getIncorrectQuizList(stage: Int): Flow<DataResourceResult<List<Int>>>
}