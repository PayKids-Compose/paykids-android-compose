package com.paykidscompose.presentation.screen.quiz.section

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.ui.components.AppTopBar
import com.paykidscompose.presentation.ui.theme.Black
import com.paykidscompose.presentation.ui.theme.QuizAppBarShadowColor
import com.paykidscompose.presentation.ui.theme.QuizAppBarShadowElevation
import com.paykidscompose.presentation.ui.theme.QuizAppBarTextStyle

@Composable
fun QuizTopBar(quizNumber: Int, totalQuizCount: Int, onBackClick: () -> Unit) {
    AppTopBar(
        title = stringResource(R.string.text_quiz_number, quizNumber, totalQuizCount),
        titleStyle = QuizAppBarTextStyle,
        showBackButton = true,
        onBackClick = onBackClick,
        iconTint = Black,
        shadowColor = QuizAppBarShadowColor,
        shadowElevation = QuizAppBarShadowElevation
    )
}