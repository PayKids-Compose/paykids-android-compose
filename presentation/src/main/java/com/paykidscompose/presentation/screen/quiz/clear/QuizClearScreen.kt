package com.paykidscompose.presentation.screen.quiz.clear

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.paykidscompose.common.enums.QuizClearType
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.audio.AudioManager
import com.paykidscompose.presentation.audio.SoundEffect
import com.paykidscompose.presentation.model.quiz.QuizClearConfigUIModel
import com.paykidscompose.presentation.model.quiz.toConfig
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
fun QuizClear(
    clearType: QuizClearType,
    onExitClick: () -> Unit = {},
    onWrongNoteClick: () -> Unit = {}
) {
    val context = LocalContext.current

    val audioManager = remember { AudioManager(context) }

    DisposableEffect(Unit) {
        audioManager.loadQuizClearSounds()
        onDispose { audioManager.release() }
    }

    // 화면 진입 시 클리어/실패 효과음 재생
    LaunchedEffect(clearType) {
        val effect = when (clearType) {
            QuizClearType.ALL_CLEAR,
            QuizClearType.CLEAR_SUCCESS,
            QuizClearType.WRONG_ANSWER_QUIZ_CLEAR,
            QuizClearType.REVIEW_COMPLETED -> SoundEffect.CLEAR
            QuizClearType.CLEAR_FAILED,
            QuizClearType.WRONG_ANSWER_QUIZ_FAILED -> SoundEffect.FAIL
        }

        audioManager.awaitLoadAndPlay(effect)
    }

    QuizClearScreen(
        config = clearType.toConfig(),
        onExitClick = onExitClick,
        onWrongNoteClick = onWrongNoteClick
    )
}

@Composable
fun QuizClearScreen(
    config: QuizClearConfigUIModel,
    onExitClick: () -> Unit = {},
    onWrongNoteClick: () -> Unit = {}
) {
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
                onClick = {
                    onExitClick()
                },
                backgroundColor = White,
                textStyle = QuizClearButtonTextStyle.copy(color = Black)
            )

            if (config.showReviewButton) {
                Spacer(modifier = Modifier.height(QuizClearButtonSpacer))
                DecisionButton(
                    text = stringResource(R.string.text_btn_review),
                    onClick = onWrongNoteClick,
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
        QuizClear(QuizClearType.ALL_CLEAR)
    }
}