package com.paykidscompose.presentation.dummy

import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.model.QuizUIModel
import com.paykidscompose.presentation.model.type.QuizType

object DummyQuiz {
    private val dummyQuizzes = listOf(
        QuizUIModel(
            stage = 1,
            number = 1,
            quizType = QuizType.TEXT_CHOICE,
            question = "다음 중 돈을 가장 잘 사용하는 방법은 무엇일까요?",
            choices = listOf(
                "A" to "필요한 물건만 산다",
                "B" to "원하는 것은 다 산다",
                "C" to "친구가 사는 걸 따라서 산다"
            ),
            answer = "A",
            imageUrl = null,
            totalCount = 5
        ),
        QuizUIModel(
            stage = 1,
            number = 2,
            quizType = QuizType.SHORT_ANSWER,
            question = "10000원 권 지폐에 그려진 인물은?",
            choices = null,
            answer = "세종대왕",
            imageUrl = null,
            totalCount = 5
        ),
        QuizUIModel(
            stage = 1,
            number = 3,
            quizType = QuizType.TEXT_CHOICE_IMAGE,
            question = "합리적인 소비법",
            choices = listOf(
                "A" to "ㅁㅁㅁ",
                "B" to "ㄴㄴㄴ",
                "C" to "ㄹㄹㄹ",
                "D" to "ㄱㄱㄱ"
            ),
            answer = "A",
            imageUrl = listOf(
                R.drawable.img_quiz_item_default,
                R.drawable.img_quiz_item_default,
                R.drawable.img_quiz_item_default,
                R.drawable.img_quiz_item_default
            ),
            totalCount = 5
        ),
        QuizUIModel(
            stage = 1,
            number = 4,
            quizType = QuizType.SHORT_ANSWER,
            question = "1000원 권 지폐에 그려진 인물은?",
            choices = null,
            answer = "퇴계이황",
            imageUrl = null,
            totalCount = 5
        ),
        QuizUIModel(
            stage = 1,
            number = 5,
            quizType = QuizType.TEXT_CHOICE,
            question = "다음 중 계좌를 만들 수 있는 곳은?",
            choices = listOf(
                "A" to "학원",
                "B" to "친구 집",
                "C" to "은행",
                "D" to "학원"
            ),
            answer = "C",
            imageUrl = null,
            totalCount = 5
        ),
    )

    fun getQuiz(stage: Int, number: Int): QuizUIModel? {
        return dummyQuizzes.firstOrNull { it.stage == stage && it.number == number }
    }

    fun getWrongNoteQuizList(stage: Int, wrongQuizNumbers: List<Int>): List<QuizUIModel> {
        return wrongQuizNumbers.mapNotNull { number ->
            getQuiz(stage, number)
        }
    }
}