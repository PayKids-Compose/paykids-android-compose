package com.paykidscompose.presentation.screens.quiz

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.model.type.QuizClearType
import com.paykidscompose.presentation.model.type.QuizType
import com.paykidscompose.presentation.ui.components.AppTopBar
import com.paykidscompose.presentation.ui.components.PopupDialog
import com.paykidscompose.presentation.ui.components.QuizResultCard
import com.paykidscompose.presentation.ui.components.util.PopupType
import com.paykidscompose.presentation.model.type.QuizResultType
import com.paykidscompose.presentation.screens.quiz.section.ImageQuizContent
import com.paykidscompose.presentation.screens.quiz.section.ShortAnswerQuizContent
import com.paykidscompose.presentation.screens.quiz.section.TextChoiceQuizContent
import com.paykidscompose.presentation.ui.theme.Black
import com.paykidscompose.presentation.ui.theme.QuizAppBarShadowColor
import com.paykidscompose.presentation.ui.theme.QuizAppBarShadowElevation
import com.paykidscompose.presentation.ui.theme.QuizAppBarTextStyle
import com.paykidscompose.presentation.ui.theme.QuizQuestionTextSpacer
import com.paykidscompose.presentation.ui.theme.QuizQuestionTextStyle
import com.paykidscompose.presentation.ui.theme.QuizResultCardSpacer
import com.paykidscompose.presentation.ui.theme.QuizResultCardTextChoiceImageSmallSpacer
import com.paykidscompose.presentation.ui.theme.QuizResultCardTextChoiceImageSpacer
import com.paykidscompose.presentation.ui.theme.QuizResultCardTextChoiceImageTopPadding
import com.paykidscompose.presentation.ui.theme.StartAndEndPadding
import com.paykidscompose.presentation.ui.theme.TextChoiceQuizImageRound

@Preview(showBackground = true)
@Composable
fun QuizScreen(
    stageNumber: Int = 1,
    onBackClick: () -> Unit = {},
    onConfirmClick: (QuizClearType) -> Unit = {},
    quizType: QuizType = QuizType.SHORT_ANSWER,
    quizNumber: Int = 2,
    totalQuizCount: Int = 7,
    question: String = "10,000원 권 지폐에는\n어떤 인물이 그려져 있을까요?",
    imgChoices: List<Pair<Int, String>> = listOf(
        R.drawable.img_quiz_item_default to "세종대왕",
        R.drawable.img_quiz_item_default to "신사임당",
        R.drawable.img_quiz_item_default to "퇴계 이황",
        R.drawable.img_quiz_item_default to "이순신"
    ),
    textChoices: List<String> = listOf(
        "10,000원 권 지폐에는 조선 시대의 왕이자 한글을 창시한 세종대왕이 그려져 있다.",
        "10,000원 권 지폐에는 BTS가 그려져 있다.",
        "로또 되고 싶다♡",
        "dd"
    ),
    image: Int = R.drawable.img_king_sejong,
    answer: String = "A",
    onChoiceClick: (Int) -> Unit = {},
    onConfirmAnswer: (Boolean) -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    var isCorrect by remember { mutableStateOf(QuizResultType.DEFAULT) }
    var userInput by remember { mutableStateOf("") }

    BackHandler(enabled = true) {
        showDialog = true
    }

    if (showDialog) {
        PopupDialog(
            title = stringResource(R.string.dialog_check_exit),
            popupType = PopupType.QUIZ_EXIT,
            onCancelClick = {
                showDialog = false
            },
            onConfirmClick = {
                showDialog = false
                onBackClick()
            },
            description = ""
        )
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.text_quiz_number, quizNumber, totalQuizCount),
                titleStyle = QuizAppBarTextStyle,
                showBackButton = true,
                onBackClick = { showDialog = true },
                iconTint = Black,
                shadowColor = QuizAppBarShadowColor,
                shadowElevation = QuizAppBarShadowElevation
            )
        }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding()
                )
        ) {
            AsyncImage(
                model = when (isCorrect) {
                    QuizResultType.DEFAULT -> R.drawable.bg_quiz_default
                    QuizResultType.CORRECT -> R.drawable.bg_quiz_correct
                    QuizResultType.WRONG -> R.drawable.bg_quiz_wrong
                },
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = StartAndEndPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = question,
                    style = QuizQuestionTextStyle,
                    color = Black,
                    textAlign = TextAlign.Center
                )

                // 정답/오답 결과 카드 보여주기
                if (quizType != QuizType.TEXT_CHOICE_IMAGE && quizType != QuizType.SHORT_ANSWER_IMAGE) { // 객관식(이미지), 주관식(이미지) 퀴즈는 이미지 위에 겹쳐서 표시
                    if (isCorrect != QuizResultType.DEFAULT) {
                        Spacer(modifier = Modifier.height(QuizResultCardSpacer))
                        QuizResultCard(
                            isCorrect = isCorrect == QuizResultType.CORRECT
                        )
                        Spacer(modifier = Modifier.height(QuizResultCardSpacer))
                    } else {
                        Spacer(modifier = Modifier.height(QuizQuestionTextSpacer))
                    }
                }

                when (quizType) {
                    QuizType.IMAGE ->
                        ImageQuizContent(
                            imgChoices = imgChoices,
                            answer = answer,
                            selectedIndex = selectedIndex,
                            isCorrect = isCorrect,
                            onChoiceClick = { index ->
                                selectedIndex = index
                                isCorrect =
                                    if (index == (answer[0] - 'A')) QuizResultType.CORRECT else QuizResultType.WRONG
                                onChoiceClick(index)
                            }
                        )

                    QuizType.TEXT_CHOICE ->
                        TextChoiceQuizContent(
                            textChoices = textChoices,
                            answer = answer,
                            selectedIndex = selectedIndex,
                            isCorrect = isCorrect,
                            onChoiceClick = { index ->
                                selectedIndex = index
                                isCorrect =
                                    if (index == (answer[0] - 'A')) QuizResultType.CORRECT else QuizResultType.WRONG
                                onChoiceClick(index)
                            }
                        )

                    QuizType.TEXT_CHOICE_IMAGE ->
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(
                                modifier = Modifier.height(
                                    QuizResultCardTextChoiceImageSmallSpacer
                                )
                            )

                            //Box로 이미지와 정답 카드 겹치기
                            Box(modifier = Modifier.fillMaxWidth()) {
                                AsyncImage(
                                    model = image,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(TextChoiceQuizImageRound))
                                        .fillMaxWidth()
                                        .padding(top = QuizResultCardTextChoiceImageTopPadding),
                                    contentScale = ContentScale.FillWidth,
                                )

                                if (isCorrect != QuizResultType.DEFAULT) {
                                    QuizResultCard(
                                        isCorrect = isCorrect == QuizResultType.CORRECT,
                                        modifier = Modifier
                                            .align(Alignment.TopCenter)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(QuizResultCardTextChoiceImageSpacer))

                            TextChoiceQuizContent(
                                textChoices = textChoices,
                                answer = answer,
                                selectedIndex = selectedIndex,
                                isCorrect = isCorrect,
                                onChoiceClick = { index ->
                                    selectedIndex = index
                                    isCorrect =
                                        if (index == (answer[0] - 'A')) QuizResultType.CORRECT else QuizResultType.WRONG
                                    onChoiceClick(index)
                                }
                            )
                        }

                    QuizType.SHORT_ANSWER ->
                        ShortAnswerQuizContent(
                            quizType = quizType,
                            userInput = userInput,
                            answer = answer,
                            onUserInputChange = { userInput = it },
                            onConfirmAnswer = { isCorrectAnswer ->
                                isCorrect =
                                    if (isCorrectAnswer) QuizResultType.CORRECT else QuizResultType.WRONG
                                onConfirmAnswer(isCorrectAnswer)
                                onConfirmClick(QuizClearType.ALL_CLEAR)
                            }
                        )

                    QuizType.SHORT_ANSWER_IMAGE ->
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(
                                modifier = Modifier.height(
                                    QuizResultCardTextChoiceImageSmallSpacer
                                )
                            )

                            //Box로 이미지와 정답 카드 겹치기
                            Box(modifier = Modifier.fillMaxWidth()) {
                                AsyncImage(
                                    model = image,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(TextChoiceQuizImageRound))
                                        .fillMaxWidth()
                                        .padding(top = QuizResultCardTextChoiceImageTopPadding),
                                    contentScale = ContentScale.FillWidth,
                                )

                                if (isCorrect != QuizResultType.DEFAULT) {
                                    QuizResultCard(
                                        isCorrect = isCorrect == QuizResultType.CORRECT,
                                        modifier = Modifier
                                            .align(Alignment.TopCenter)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(QuizResultCardTextChoiceImageSpacer))

                            ShortAnswerQuizContent(
                                quizType = quizType,
                                userInput = userInput,
                                answer = answer,
                                onUserInputChange = { userInput = it },
                                onConfirmAnswer = { isCorrectAnswer ->
                                    isCorrect =
                                        if (isCorrectAnswer) QuizResultType.CORRECT else QuizResultType.WRONG
                                    onConfirmAnswer(isCorrectAnswer)
                                }
                            )
                        }
                }
            }
        }
    }

}