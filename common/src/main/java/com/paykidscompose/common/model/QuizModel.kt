package com.paykidscompose.common.model

data class QuizModel (
    val id: Long,
    val stage: Int,
    val number: Int,
    val count: Int,
    val quizType: String,
    val question: String,
    val choices: Map<String, String>,
    val answer: String,
    val imageURL: Map<String, String>
): Model()