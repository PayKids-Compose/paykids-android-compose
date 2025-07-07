package com.paykidscompose.data.model.quiz

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuizDTO(
    @Json(name = "id")
    val id: Long,

    @Json(name = "stage")
    val stage: Int,

    @Json(name = "number")
    val number: Int,

    @Json(name = "count")
    val count: Int,

    @Json(name = "quizType")
    val quizType: String,

    @Json(name = "question")
    val question: String,

    @Json(name = "choices")
    val choices: Map<String, String>,

    @Json(name = "answer")
    val answer: String,

    @Json(name = "imageURL")
    val imageURL: Map<String, String>
)
