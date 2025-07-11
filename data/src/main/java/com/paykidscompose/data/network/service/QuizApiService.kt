package com.paykidscompose.data.network.service

import com.paykidscompose.data.model.BaseResponse
import com.paykidscompose.data.model.quiz.QuizClearedDTO
import com.paykidscompose.data.model.quiz.QuizDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizApiService {
    @GET("/quiz")
    suspend fun getQuiz(
        @Query("stage") stage: Int,
        @Query("number") number: Int
    ): BaseResponse<QuizDTO>

    @GET("/quiz/stage-to-go")
    suspend fun getStageToGo(): BaseResponse<Int>

    @GET("/quiz/stage-name")
    suspend fun getStageName(@Query("stage") stage: Int): BaseResponse<String>

    @GET("/quiz/incorrect-list")
    suspend fun getIncorrectQuizList(
        @Query("stage") stage: Int
    ): BaseResponse<List<Int>>

    @GET("/quiz/count")
    suspend fun getStageCount(): BaseResponse<Int>

    @GET("/quiz/check-stage")
    suspend fun getCheckStage(
        @Query("stage") stage: Int
    ): BaseResponse<QuizClearedDTO>

    @GET("/quiz/check-answer")
    suspend fun getCheckAnswer(
        @Query("stage") stage: Int,
        @Query("number") number: Int,
        @Query("answer") answer: String
    ): BaseResponse<Boolean>
}