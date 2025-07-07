package com.paykidscompose.data.model.quiz

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuizClearedDTO(
    @Json(name = "message")
    val message: String,
    @Json(name = "isCleared")
    val isCleared: Boolean
)
