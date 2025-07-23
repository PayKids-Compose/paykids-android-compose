package com.paykidscompose.common.model

import com.paykidscompose.common.enums.QuizType

data class QuizModel (
    val id: Long,
    val stage: Int,
    val number: Int,
    val count: Int,
    val quizType: QuizType,
    val question: String,
    val choices: Map<String, String>,
    val answer: String,
    val imageURL: Map<String, String>
): Model()