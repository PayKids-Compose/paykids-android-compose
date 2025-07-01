package com.paykidscompose.presentation.screens.quiz.section

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.model.type.QuizType
import com.paykidscompose.presentation.ui.components.DecisionButton
import com.paykidscompose.presentation.ui.components.OutlineInputField
import com.paykidscompose.presentation.ui.theme.Blue2
import com.paykidscompose.presentation.ui.theme.CardShadowElevation
import com.paykidscompose.presentation.ui.theme.Gray2
import com.paykidscompose.presentation.ui.theme.PayKidsComposeTheme
import com.paykidscompose.presentation.ui.theme.QuizShortAnswerTextStyle
import com.paykidscompose.presentation.ui.theme.ShortAnswerImageQuizContentSpacer
import com.paykidscompose.presentation.ui.theme.ShortAnswerQuizContentSpacer
import com.paykidscompose.presentation.ui.theme.ShortAnswerQuizOutlineHeight
import com.paykidscompose.presentation.ui.theme.ShortAnswerQuizOutlineShape
import com.paykidscompose.presentation.ui.theme.ShortAnswerQuizOutlineStartPadding
import com.paykidscompose.presentation.ui.theme.White

@Composable
fun ShortAnswerQuizContent(
    quizType: QuizType,
    userInput: String,
    answer: String,
    onUserInputChange: (String) -> Unit,
    onConfirmAnswer: (Boolean) -> Unit
) {
    var isSubmitted by remember { mutableStateOf(false) }
    val isCorrectAnswer = userInput.trim() == answer.trim()

    OutlineInputField(
        text = userInput,
        onTextChange = {
            if (isSubmitted) isSubmitted = false
            onUserInputChange(it)
        },
        startPadding = ShortAnswerQuizOutlineStartPadding,
        modifier = Modifier
            .height(ShortAnswerQuizOutlineHeight),
        backgroundColor = White,
        outlineColor = Gray2,
        hint = stringResource(R.string.hint_input_answer),
        hintColor = Gray2,
        style = QuizShortAnswerTextStyle,
        shape = RoundedCornerShape(ShortAnswerQuizOutlineShape),
        enabled = !isSubmitted,
        shadowElevation = CardShadowElevation,
        shadowColor = Gray2
    )

    Spacer(
        modifier = if (quizType == QuizType.SHORT_ANSWER) {
            Modifier.height(ShortAnswerQuizContentSpacer)
        } else {
            Modifier.height(ShortAnswerImageQuizContentSpacer)
        }
    )

    DecisionButton(
        text = stringResource(R.string.text_confirm_nickname), onClick = {
            if (!isSubmitted) {
                onConfirmAnswer(isCorrectAnswer)
                isSubmitted = true
            }
        },
        enabled = !isSubmitted, backgroundColor = if (isSubmitted) Gray2 else Blue2
    )
}

@Preview
@Composable
fun ShortAnswerQuizContentPreview() {
    PayKidsComposeTheme {
        var userInput by remember { mutableStateOf("") }

        Column(modifier = Modifier.wrapContentHeight()) {
            ShortAnswerQuizContent(
                quizType = QuizType.SHORT_ANSWER_IMAGE,
                userInput = userInput,
                answer = "세종대왕",
                onUserInputChange = { userInput = it },
                onConfirmAnswer = {})
        }
    }
}