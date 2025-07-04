package com.paykidscompose.presentation.screens.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.dummy.getStageTitle
import com.paykidscompose.presentation.ui.components.DecisionButton
import com.paykidscompose.presentation.ui.components.PopupDialog
import com.paykidscompose.presentation.ui.components.util.PopupType
import com.paykidscompose.presentation.ui.theme.Blue1
import com.paykidscompose.presentation.ui.theme.DeterminationButtonTextTopAndBottom2
import com.paykidscompose.presentation.ui.theme.DeterminationTextStyle2
import com.paykidscompose.presentation.ui.theme.Gray5
import com.paykidscompose.presentation.ui.theme.PayKidsComposeTheme
import com.paykidscompose.presentation.ui.theme.QuizEntryButtonSpacer
import com.paykidscompose.presentation.ui.theme.QuizEntryScreenBottomPadding
import com.paykidscompose.presentation.ui.theme.QuizEntryScreenSpacer1
import com.paykidscompose.presentation.ui.theme.QuizEntryScreenSpacer2
import com.paykidscompose.presentation.ui.theme.QuizEntryScreenTopPadding
import com.paykidscompose.presentation.ui.theme.StageNumberCardHorizontalPadding
import com.paykidscompose.presentation.ui.theme.StageNumberCardRound
import com.paykidscompose.presentation.ui.theme.StageNumberCardTextStyle
import com.paykidscompose.presentation.ui.theme.StageNumberCardVerticalPadding
import com.paykidscompose.presentation.ui.theme.StageTooltipTextStyle
import com.paykidscompose.presentation.ui.theme.StartAndEndPadding
import com.paykidscompose.presentation.ui.theme.White

@Composable
fun QuizEntry(
    stageNumber: Int,
    onShowDialogChange: (Boolean) -> Unit = {},
    onQuiz: (Int) -> Unit = {},
    onStudyClick: () -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }

    QuizEntryScreen(
        stageNumber = stageNumber,
        showDialog = showDialog,
        onShowDialogChange = onShowDialogChange,
        onQuiz = onQuiz,
        onStudyClick = onStudyClick
    )
}

@Composable
fun QuizEntryScreen(
    stageNumber: Int,
    showDialog: Boolean,
    onShowDialogChange: (Boolean) -> Unit,
    onQuiz: (Int) -> Unit = {},
    onStudyClick: () -> Unit = {}
) {
    val stageTitle = getStageTitle(stageNumber - 1)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (showDialog) {
            PopupDialog(
                title = stringResource(R.string.dialog_incorrect_nothing),
                popupType = PopupType.INCORRECT_ANSWER_NOTE_ERROR,
                onCancelClick = { onShowDialogChange(false) },
                onConfirmClick = { onShowDialogChange(false) },
                description = ""
            )
        }

        AsyncImage(
            model = R.drawable.bg_quiz_enter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = StartAndEndPadding)
                .padding(top = QuizEntryScreenTopPadding, bottom = QuizEntryScreenBottomPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier,
                shape = RoundedCornerShape(StageNumberCardRound),
                colors = CardDefaults.cardColors(containerColor = Gray5)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = StageNumberCardHorizontalPadding, vertical = StageNumberCardVerticalPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = stringResource(R.string.text_stage_number, stageNumber), style = StageNumberCardTextStyle)
                }
            }
            Spacer(modifier = Modifier.height(QuizEntryScreenSpacer1))

            Text(text = stageTitle, style = StageTooltipTextStyle, color = White)
            Spacer(modifier = Modifier.height(QuizEntryScreenSpacer2))

            DecisionButton( // 학습하기
                text = stringResource(R.string.text_btn_study),
                onClick = {
                    onStudyClick()
                },
                backgroundColor = White,
                contentColor = Blue1,
                contentPadding = DeterminationButtonTextTopAndBottom2,
                textStyle = DeterminationTextStyle2
            )
            Spacer(modifier = Modifier.height(QuizEntryButtonSpacer))
            DecisionButton( // 퀴즈 풀기
                text = stringResource(R.string.text_btn_quiz),
                onClick = {
                    onQuiz(stageNumber)
                },
                backgroundColor = Blue1,
                contentColor = White,
                contentPadding = DeterminationButtonTextTopAndBottom2,
                textStyle = DeterminationTextStyle2
            )
            Spacer(modifier = Modifier.height(QuizEntryButtonSpacer))
            DecisionButton( // 오답노트 풀기
                text = stringResource(R.string.text_btn_review),
                onClick = {
                    onShowDialogChange(true)
                },
                backgroundColor = White,
                contentColor = Blue1,
                contentPadding = DeterminationButtonTextTopAndBottom2,
                textStyle = DeterminationTextStyle2
            )
        }

    }
}

@Preview
@Composable
fun QuizEntryScreenPreview(){
    PayKidsComposeTheme {
        QuizEntry(2)
    }
}