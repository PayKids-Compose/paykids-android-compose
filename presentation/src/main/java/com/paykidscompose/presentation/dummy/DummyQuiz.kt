package com.paykidscompose.presentation.dummy

import androidx.annotation.DrawableRes
import com.paykidscompose.presentation.R

data class Quiz(
    val id: Int,
    val stage: Int,
    val number: Int,
    val count: Int,
    val quizType: String,
    val question: String,
    val choices: List<Pair<String, String>>,
    val answer: String,
    @DrawableRes
    val imageUrl: List<Int>
)

object DummyQuiz {
    fun getQuiz() = listOf(
        Quiz(
            id = 1,
            stage = 1,
            number = 1,
            count = 8,
            quizType = "IMAGE_CHOICE",
            question = "우리나라에서 발행하는 종이로 된 돈을 무엇이라 할까요?",
            choices = listOf(
                Pair("A", "동전"),
                Pair("B", "수표"),
                Pair("C", "지폐"),
                Pair("D", "영수증")
            ),
            answer = "C",
            imageUrl = listOf(
                R.drawable.img_quiz_item_default,
                R.drawable.img_quiz_item_default,
                R.drawable.img_quiz_item_default,
                R.drawable.img_quiz_item_default
            )
        ),
        Quiz(
            2,
            1,
            2,
            8,
            "SHORT_ANSWER",
            "1000원은 100원이 몇 개 모인 금액일까요?",
            emptyList(),
            "10개",
            emptyList()
        )
    )
}