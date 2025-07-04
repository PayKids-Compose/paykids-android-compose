package com.paykidscompose.presentation.dummy

import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.model.type.QuizType

data class Quiz(
    val id: Int,
    val stage: Int,
    val number: Int,
    val quizType: QuizType,
    val question: String,
    val choices: List<Pair<String, String>>?,
    val answer: String,
    val imageUrl: List<Int>?,
    val totalCount: Int
)

object DummyQuiz {
    private val dummyQuizzes = listOf(
        // --- Stage 1 ---
        Quiz(
            id = 1,
            stage = 1,
            number = 1,
            quizType = QuizType.TEXT_CHOICE,
            question = "돈을 벌기 위해 우리가 하는 일을 무엇이라고 하나요?",
            choices = listOf(
                "A" to "놀이",
                "B" to "직업",
                "C" to "여행",
                "D" to "숙제"
            ),
            answer = "B",
            imageUrl = null,
            totalCount = 6
        ),
        Quiz(
            id = 2,
            stage = 1,
            number = 2,
            quizType = QuizType.SHORT_ANSWER,
            question = "은행에 돈을 맡기는 것을 무엇이라고 할까요?",
            choices = null,
            answer = "저축",
            imageUrl = null,
            totalCount = 6
        ),
        Quiz(
            id = 3,
            stage = 1,
            number = 3,
            quizType = QuizType.IMAGE,
            question = "다음 중 지폐는 무엇일까요?",
            choices = listOf(
                "A" to "지갑",
                "B" to "10000원",
                "C" to "신용카드",
                "D" to "동전"
            ),
            answer = "B",
            imageUrl = listOf(
                R.drawable.img_quiz_item_default,
                R.drawable.img_quiz_item_default,
                R.drawable.img_quiz_item_default,
                R.drawable.img_quiz_item_default
            ),
            totalCount = 6
        ),
        Quiz(
            id = 4,
            stage = 1,
            number = 4,
            quizType = QuizType.TEXT_CHOICE_IMAGE,
            question = "필요한 것을 사는 것을 무엇이라고 하나요?",
            choices = listOf(
                "A" to "소비",
                "B" to "기부",
                "C" to "저축",
                "D" to "일"
            ),
            answer = "A",
            imageUrl = listOf(R.drawable.img_quiz_item_default),
            totalCount = 6
        ),
        Quiz(
            id = 5,
            stage = 1,
            number = 5,
            quizType = QuizType.SHORT_ANSWER_IMAGE,
            question = "사진 속 인물은 누구인가요?",
            choices = null,
            answer = "세종대왕",
            imageUrl = listOf(R.drawable.img_king_sejong),
            totalCount = 6
        ),
        Quiz(
            id = 6,
            stage = 1,
            number = 6,
            quizType = QuizType.TEXT_CHOICE,
            question = "돈을 모아두는 것을 무엇이라고 하나요?",
            choices = listOf(
                "A" to "기부",
                "B" to "저축",
                "C" to "소비",
                "D" to "대출"
            ),
            answer = "B",
            imageUrl = null,
            totalCount = 6
        ),

        // --- Stage 2 ---
        Quiz(
            id = 7,
            stage = 2,
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
        Quiz(
            id = 8,
            stage = 2,
            number = 2,
            quizType = QuizType.SHORT_ANSWER,
            question = "10000원 권 지폐에 그려진 인물은?",
            choices = null,
            answer = "세종대왕",
            imageUrl = null,
            totalCount = 5
        ),
        Quiz(
            id = 9,
            stage = 2,
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
        Quiz(
            id = 10,
            stage = 2,
            number = 4,
            quizType = QuizType.SHORT_ANSWER,
            question = "1000원 권 지폐에 그려진 인물은?",
            choices = null,
            answer = "퇴계이황",
            imageUrl = null,
            totalCount = 5
        ),
        Quiz(
            id = 11,
            stage = 2,
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

    fun getQuiz(stage: Int, number: Int): Quiz? {
        return dummyQuizzes.firstOrNull { it.stage == stage && it.number == number }
    }

    fun getQuizzes(stage: Int): List<Quiz> {
        return dummyQuizzes.filter { it.stage == stage }.sortedBy { it.number }
    }

    fun getWrongAnswerNoteQuizzes(stage: Int, wrongAnswerQuizNumbers: List<Int>): List<Quiz> {
        return wrongAnswerQuizNumbers.mapNotNull { number ->
            getQuiz(stage, number)
        }
    }
}