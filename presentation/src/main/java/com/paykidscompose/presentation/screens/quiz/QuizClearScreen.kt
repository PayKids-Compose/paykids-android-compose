package com.paykidscompose.presentation.screens.quiz

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.model.QuizClearType
import com.paykidscompose.presentation.model.toConfig
import com.paykidscompose.presentation.ui.components.DashedCard
import com.paykidscompose.presentation.ui.components.DecisionButton
import com.paykidscompose.presentation.ui.theme.Black
import com.paykidscompose.presentation.ui.theme.Blue2
import com.paykidscompose.presentation.ui.theme.PayKidsComposeTheme
import com.paykidscompose.presentation.ui.theme.QuizClearButtonSpacer
import com.paykidscompose.presentation.ui.theme.QuizClearButtonTextStyle
import com.paykidscompose.presentation.ui.theme.QuizClearScreenColumnBottomPadding
import com.paykidscompose.presentation.ui.theme.QuizClearScreenColumnTopPadding
import com.paykidscompose.presentation.ui.theme.StartAndEndPadding
import com.paykidscompose.presentation.ui.theme.White


@Composable
fun QuizClearScreen() {
    val isClear = remember { mutableStateOf(QuizClearType.ALL_CLEAR) }

    val config = isClear.value.toConfig()

    Box(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = config.bgRes,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = StartAndEndPadding)
                .padding(
                    top = QuizClearScreenColumnTopPadding,
                    bottom = QuizClearScreenColumnBottomPadding
                )
        ) {
            DashedCard(text = stringResource(config.textRes), shadowColor = config.shadowColor, dashedBorderColor = config.dashedBorderColor)
            Spacer(modifier = Modifier.height(config.spacerHeight))

            DecisionButton(
                text = stringResource(R.string.dialog_exit2),
                onClick = {},
                backgroundColor = White,
                textStyle = QuizClearButtonTextStyle.copy(color = Black)
            )

            if (config.showReviewButton) {
                Spacer(modifier = Modifier.height(QuizClearButtonSpacer))
                DecisionButton(
                    text = stringResource(R.string.text_btn_review),
                    onClick = {},
                    backgroundColor = Blue2,
                    textStyle = QuizClearButtonTextStyle.copy(color = White)
                )
            }
        }
    }
}


@Preview
@Composable
fun QuizClearScreenPreview() {
    PayKidsComposeTheme {
        QuizClearScreen()
    }
}