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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.model.type.QuizClearType
import com.paykidscompose.presentation.model.type.QuizResultType
import com.paykidscompose.presentation.model.type.QuizType
import com.paykidscompose.presentation.screens.quiz.section.ImageQuizContent
import com.paykidscompose.presentation.screens.quiz.section.QuizTopBar
import com.paykidscompose.presentation.screens.quiz.section.ShortAnswerQuizContent
import com.paykidscompose.presentation.screens.quiz.section.TextChoiceQuizContent
import com.paykidscompose.presentation.ui.components.PopupDialog
import com.paykidscompose.presentation.ui.components.QuizResultCard
import com.paykidscompose.presentation.ui.components.util.PopupType
import com.paykidscompose.presentation.ui.theme.Black
import com.paykidscompose.presentation.ui.theme.QuizQuestionTextSpacer
import com.paykidscompose.presentation.ui.theme.QuizQuestionTextStyle
import com.paykidscompose.presentation.ui.theme.QuizResultCardSpacer
import com.paykidscompose.presentation.ui.theme.QuizResultCardTextChoiceImageSmallSpacer
import com.paykidscompose.presentation.ui.theme.QuizResultCardTextChoiceImageSpacer
import com.paykidscompose.presentation.ui.theme.QuizResultCardTextChoiceImageTopPadding
import com.paykidscompose.presentation.ui.theme.StartAndEndPadding
import com.paykidscompose.presentation.ui.theme.TextChoiceQuizImageRound
import kotlinx.coroutines.delay

@Composable
fun Quiz(
    stageNumber: Int,
    quizViewModel: QuizViewModel = viewModel(),
    onBackClick: () -> Unit,
    onConfirmClick: (QuizClearType) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    var isCorrect by remember { mutableStateOf(QuizResultType.DEFAULT) }
    var userInput by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        quizViewModel.loadQuiz(stageNumber)
    }

    QuizScreen(
        stageNumber = stageNumber,
        quizViewModel = quizViewModel,
        showDialog = showDialog,
        onShowDialogChange = { showDialog = it },
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { selectedIndex = it },
        isCorrect = isCorrect,
        onCorrectChange = { isCorrect = it },
        userInput = userInput,
        onUserInputChange = { userInput = it },
        onBackClick = onBackClick,
        onConfirmClick = onConfirmClick,
        onChoiceClick = { selectedIndex = it },
        onConfirmAnswer = { isCorrectAnswer ->
            isCorrect = if (isCorrectAnswer) QuizResultType.CORRECT else QuizResultType.WRONG
        }
    )
}

@Composable
fun QuizScreen(
    stageNumber: Int = 1,
    quizViewModel: QuizViewModel,
    showDialog: Boolean,
    onShowDialogChange: (Boolean) -> Unit,
    selectedIndex: Int?,
    onSelectedIndexChange: (Int?) -> Unit,
    isCorrect: QuizResultType,
    onCorrectChange: (QuizResultType) -> Unit,
    userInput: String,
    onUserInputChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onConfirmClick: (QuizClearType) -> Unit,
    onChoiceClick: (Int) -> Unit,
    onConfirmAnswer: (Boolean) -> Unit
) {
    val currentQuiz = quizViewModel.currentQuiz
    if (currentQuiz == null) {
        // 퀴즈 없을 때 임시로 보여줄 화면
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = stringResource(R.string.text_quiz_loading), style = QuizQuestionTextStyle)
        }
        return
    }

    val totalCount = currentQuiz.totalCount
    val quizNumber = quizViewModel.currentIndex + 1

    BackHandler(enabled = true) {
        onShowDialogChange(true)
    }

    if (showDialog) {
        PopupDialog(
            title = stringResource(R.string.dialog_check_exit),
            popupType = PopupType.QUIZ_EXIT,
            onCancelClick = {
                onShowDialogChange(false)
            },
            onConfirmClick = {
                onShowDialogChange(false)
                onBackClick()
            },
            description = ""
        )
    }

    LaunchedEffect(isCorrect) {
        if (isCorrect != QuizResultType.DEFAULT) {
            delay(2000L)
            if (quizViewModel.isLastQuiz()) {
                onConfirmClick(QuizClearType.ALL_CLEAR)
            } else {
                quizViewModel.moveToNext()
                onSelectedIndexChange(null)
                onCorrectChange(QuizResultType.DEFAULT)
                onUserInputChange("")
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        QuizTopBar(
            quizNumber = quizNumber,
            totalQuizCount = totalCount,
            onBackClick = { onShowDialogChange(true) }
        )
        Box(
            modifier = Modifier.fillMaxSize()
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
                    text = currentQuiz.question,
                    style = QuizQuestionTextStyle,
                    color = Black,
                    textAlign = TextAlign.Center
                )

                // 정답/오답 결과 카드 보여주기
                if (currentQuiz.quizType != QuizType.TEXT_CHOICE_IMAGE && currentQuiz.quizType != QuizType.SHORT_ANSWER_IMAGE) { // 객관식(이미지), 주관식(이미지) 퀴즈는 이미지 위에 겹쳐서 표시
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

                when (currentQuiz.quizType) {
                    QuizType.IMAGE -> {
                        val imageChoices: List<Pair<Int, String>> = currentQuiz.imageUrl
                            ?.mapIndexed { index, resId ->
                                val label = currentQuiz.choices?.getOrNull(index)?.second ?: ""
                                resId to label
                            } ?: emptyList()
                        ImageQuizContent(
                            imgChoices = imageChoices,
                            answer = currentQuiz.answer,
                            selectedIndex = selectedIndex,
                            isCorrect = isCorrect,
                            onChoiceClick = { index ->
                                onSelectedIndexChange(index)
                                val isRight = index == (currentQuiz.answer[0] - 'A')
                                onCorrectChange(if (isRight) QuizResultType.CORRECT else QuizResultType.WRONG)
                                onChoiceClick(index)
                            }
                        )
                    }

                    QuizType.TEXT_CHOICE -> {
                        val textChoices: List<String> = currentQuiz.choices?.map { it.second } ?: emptyList()
                        TextChoiceQuizContent(
                            textChoices = textChoices,
                            answer = currentQuiz.answer,
                            selectedIndex = selectedIndex,
                            isCorrect = isCorrect,
                            onChoiceClick = { index ->
                                onSelectedIndexChange(index)
                                val isRight = index == (currentQuiz.answer[0] - 'A')
                                onCorrectChange(if (isRight) QuizResultType.CORRECT else QuizResultType.WRONG)
                                onChoiceClick(index)
                            }
                        )
                    }

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
                                    model = currentQuiz.imageUrl?.first(),
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

                            val textChoices: List<String> = currentQuiz.choices?.map { it.second } ?: emptyList()
                            TextChoiceQuizContent(
                                textChoices = textChoices,
                                answer = currentQuiz.answer,
                                selectedIndex = selectedIndex,
                                isCorrect = isCorrect,
                                onChoiceClick = { index ->
                                    onSelectedIndexChange(index)
                                    val isRight = index == (currentQuiz.answer[0] - 'A')
                                    onCorrectChange(if (isRight) QuizResultType.CORRECT else QuizResultType.WRONG)
                                    onChoiceClick(index)
                                }
                            )
                        }

                    QuizType.SHORT_ANSWER ->
                        ShortAnswerQuizContent(
                            quizType = currentQuiz.quizType,
                            userInput = userInput,
                            answer = currentQuiz.answer,
                            onUserInputChange = onUserInputChange,
                            onConfirmAnswer = { isCorrectAnswer ->
                                onCorrectChange(
                                    if (isCorrectAnswer) QuizResultType.CORRECT else QuizResultType.WRONG
                                )
                                onConfirmAnswer(isCorrectAnswer)
                                //onConfirmClick(QuizClearType.ALL_CLEAR)
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
                                    model = currentQuiz.imageUrl?.first(),
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
                                quizType = currentQuiz.quizType,
                                userInput = userInput,
                                answer = currentQuiz.answer,
                                onUserInputChange = onUserInputChange,
                                onConfirmAnswer = { isCorrectAnswer ->
                                    onCorrectChange(
                                        if (isCorrectAnswer) QuizResultType.CORRECT else QuizResultType.WRONG
                                    )
                                    onConfirmAnswer(isCorrectAnswer)
                                }
                            )
                        }
                }
            }
        }
    }

}