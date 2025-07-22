package com.paykidscompose.presentation.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.paykidscompose.presentation.R
import com.paykidscompose.common.enums.QuizClearType
import com.paykidscompose.presentation.ui.theme.Gray10
import com.paykidscompose.presentation.ui.theme.Gray8
import com.paykidscompose.presentation.ui.theme.Purple
import com.paykidscompose.presentation.ui.theme.Purple2
import com.paykidscompose.presentation.ui.theme.QuizClearSmallSpacer
import com.paykidscompose.presentation.ui.theme.QuizClearSpacer

data class QuizClearConfigUIModel(
    val textRes: Int,
    val bgRes: Int,
    val spacerHeight: Dp,
    val showReviewButton: Boolean,
    val shadowColor: Color,
    val dashedBorderColor: Color
): UIModel()

fun QuizClearType.toConfig(): QuizClearConfigUIModel = when (this) {
    QuizClearType.ALL_CLEAR -> QuizClearConfigUIModel(
        textRes = R.string.text_box_all_clear,
        bgRes = R.drawable.bg_quiz_clear,
        spacerHeight = QuizClearSpacer,
        showReviewButton = false,
        shadowColor = Purple,
        dashedBorderColor = Purple2
    )

    QuizClearType.CLEAR_SUCCESS -> QuizClearConfigUIModel(
        textRes = R.string.text_box_first_clear,
        bgRes = R.drawable.bg_quiz_clear,
        spacerHeight = QuizClearSmallSpacer,
        showReviewButton = true,
        shadowColor = Purple,
        dashedBorderColor = Purple2
    )

    QuizClearType.CLEAR_FAILED -> QuizClearConfigUIModel(
        textRes = R.string.text_box_failed,
        bgRes = R.drawable.bg_quiz_fail,
        spacerHeight = QuizClearSmallSpacer,
        showReviewButton = true,
        shadowColor = Gray10,
        dashedBorderColor = Gray8
    )

    QuizClearType.WRONG_ANSWER_QUIZ_CLEAR -> QuizClearConfigUIModel(
        textRes = R.string.text_box_incorrect_answer_note_clear,
        bgRes = R.drawable.bg_quiz_clear,
        spacerHeight = QuizClearSpacer,
        showReviewButton = false,
        shadowColor = Purple,
        dashedBorderColor = Purple2
    )

    QuizClearType.WRONG_ANSWER_QUIZ_FAILED -> QuizClearConfigUIModel(
        textRes = R.string.text_box_failed,
        bgRes = R.drawable.bg_quiz_fail,
        spacerHeight = QuizClearSpacer,
        showReviewButton = false,
        shadowColor = Gray10,
        dashedBorderColor = Gray8
    )

    QuizClearType.REVIEW_COMPLETED -> QuizClearConfigUIModel(
        textRes = R.string.text_box_review,
        bgRes = R.drawable.bg_quiz_clear,
        spacerHeight = QuizClearSpacer,
        showReviewButton = false,
        shadowColor = Purple,
        dashedBorderColor = Purple2
    )
}