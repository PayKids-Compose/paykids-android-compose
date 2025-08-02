package com.paykidscompose.common.model.quiz

import com.paykidscompose.common.enums.QuizType
import com.paykidscompose.common.model.Model

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