package com.paykidscompose.presentation.screens.quiz

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.paykidscompose.common.model.WrongAnswerNoteStatus
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.model.type.QuizEntryDialogType
import com.paykidscompose.presentation.ui.components.DecisionButton
import com.paykidscompose.presentation.ui.components.PopupDialog
import com.paykidscompose.presentation.ui.components.util.PopupType
import com.paykidscompose.presentation.ui.theme.Blue1
import com.paykidscompose.presentation.ui.theme.DeterminationButtonTextTopAndBottom2
import com.paykidscompose.presentation.ui.theme.DeterminationTextStyle2
import com.paykidscompose.presentation.ui.theme.Gray5
import com.paykidscompose.presentation.ui.theme.PayKidsComposeTheme
import com.paykidscompose.presentation.ui.theme.QuizEntryBackButtonHorizontalPadding
import com.paykidscompose.presentation.ui.theme.QuizEntryBackButtonVerticalPadding
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
    stageTitle: String,
    quizEntryViewModel: QuizEntryViewModel = viewModel(),
    onQuiz: (Int) -> Unit = {},
    onWrongNote: (Int) -> Unit = {},
    onStudyClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }
    var showDialogType by remember { mutableStateOf<QuizEntryDialogType?>(null) }

    QuizEntryScreen(
        stageNumber = stageNumber,
        stageTitle = stageTitle,
        quizEntryViewModel = quizEntryViewModel,
        showDialog = showDialog,
        showDialogType = showDialogType,
        onShowDialogChange = { showDialog = it },
        onShowDialogTypeChange = { showDialogType = it },
        onQuiz = onQuiz,
        onWrongNote = onWrongNote,
        onStudyClick = onStudyClick,
        onBackClick = onBackClick
    )
}

@Composable
fun QuizEntryScreen(
    stageNumber: Int,
    stageTitle: String,
    quizEntryViewModel: QuizEntryViewModel,
    showDialog: Boolean,
    showDialogType: QuizEntryDialogType?,
    onShowDialogChange: (Boolean) -> Unit,
    onShowDialogTypeChange: (QuizEntryDialogType?) -> Unit,
    onQuiz: (Int) -> Unit = {},
    onWrongNote: (Int) -> Unit = {},
    onStudyClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (showDialog) {
            val dialogText = when (showDialogType) {
                QuizEntryDialogType.AllCorrect -> stringResource(R.string.dialog_all_correct)
                QuizEntryDialogType.NoAttempt -> stringResource(R.string.dialog_incorrect_nothing)
                else -> ""
            }

            PopupDialog(
                title = dialogText,
                popupType = PopupType.INCORRECT_ANSWER_NOTE_ERROR,
                onCancelClick = { onShowDialogChange(false) },
                onConfirmClick = {
                    onShowDialogChange(false)
                    onQuiz(stageNumber)
                },
                description = ""
            )
        }

        AsyncImage(
            model = R.drawable.bg_quiz_enter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box( // 뒤로 가기 버튼 커스텀
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(
                    horizontal = QuizEntryBackButtonHorizontalPadding,
                    vertical = QuizEntryBackButtonVerticalPadding
                )
                .clickable(onClick = onBackClick)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_left_white),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }

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
                    modifier = Modifier.padding(
                        horizontal = StageNumberCardHorizontalPadding,
                        vertical = StageNumberCardVerticalPadding
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.text_stage_number, stageNumber),
                        style = StageNumberCardTextStyle
                    )
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
                    quizEntryViewModel.checkWrongAnswerStatus(
                        stageNumber,
                        onNavigateToWrongNote = { onWrongNote(stageNumber) },
                        onShowDialog = { status ->
                            when (status) {
                                WrongAnswerNoteStatus.AllCorrect -> { // 전부 맞힌 경우(All Clear or 복습)
                                    onShowDialogTypeChange(QuizEntryDialogType.AllCorrect)
                                }

                                WrongAnswerNoteStatus.NoAttempt -> { // 아직 퀴즈를 푼 적 없는 경우
                                    onShowDialogTypeChange(QuizEntryDialogType.NoAttempt)
                                }

                                else -> Unit
                            }
                            onShowDialogChange(true)
                        }
                    )
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
fun QuizEntryScreenPreview() {
    PayKidsComposeTheme {
        QuizEntry(2, "은행은 어떤 곳인가요?")
    }
}