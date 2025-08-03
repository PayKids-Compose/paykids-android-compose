package com.paykidscompose.presentation.screens.quiz

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.paykidscompose.common.enums.QuizClearType
import com.paykidscompose.common.enums.QuizType
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.model.quiz.QuizUIModel
import com.paykidscompose.presentation.model.type.QuizResultType
import com.paykidscompose.presentation.screens.quiz.section.ImageQuizContent
import com.paykidscompose.presentation.screens.quiz.section.QuizTopBar
import com.paykidscompose.presentation.screens.quiz.section.ShortAnswerQuizContent
import com.paykidscompose.presentation.screens.quiz.section.TextChoiceQuizContent
import com.paykidscompose.presentation.ui.components.PopupDialog
import com.paykidscompose.presentation.ui.components.QuizResultCard
import com.paykidscompose.presentation.ui.components.ScreenLoading
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
    isWrongAnswerNote: Boolean,
    quizViewModel: QuizViewModel = viewModel(),
    onBackClick: () -> Unit,
    onConfirmClick: (QuizClearType) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    var userInput by remember { mutableStateOf("") }

    val uiState by quizViewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(stageNumber, isWrongAnswerNote) {
        if (!isWrongAnswerNote) {
            quizViewModel.loadQuizzes(stageNumber)
        } else {
            quizViewModel.loadWrongAnswerQuizzes(stageNumber)
        }
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            when (it) {
                is PayKidsException.SnackBarException -> {
                }

                is PayKidsException.DialogException -> {
                }

                is PayKidsException.ToastException -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }

            quizViewModel.clearError()
        }
    }

    when {
        uiState.isLoading -> {
            ScreenLoading()
        }

        uiState.currentQuiz != null -> {
            QuizScreen(
                quizViewModel = quizViewModel,
                currentQuiz = uiState.currentQuiz!!,
                isLastQuiz = quizViewModel.isLastQuiz(),
                showDialog = showDialog,
                onShowDialogChange = { showDialog = it },
                selectedIndex = selectedIndex,
                onSelectedIndexChange = { selectedIndex = it },
                isCorrect = uiState.quizResultType,
                userInput = userInput,
                onUserInputChange = { userInput = it },
                quizNumber = uiState.currentQuiz!!.number,
                totalCount = uiState.totalCount,
                onBackClick = onBackClick,
                onConfirmClick = onConfirmClick,
                onNextQuiz = { quizViewModel.moveToNext() }
            )
        }
    }
}

@Composable
fun QuizScreen(
    quizViewModel: QuizViewModel,
    currentQuiz: QuizUIModel,
    isLastQuiz: Boolean,
    showDialog: Boolean,
    onShowDialogChange: (Boolean) -> Unit,
    selectedIndex: Int?,
    onSelectedIndexChange: (Int?) -> Unit,
    isCorrect: QuizResultType,
    userInput: String,
    onUserInputChange: (String) -> Unit,
    quizNumber: Int,
    totalCount: Int,
    onBackClick: () -> Unit,
    onConfirmClick: (QuizClearType) -> Unit,
    onNextQuiz: () -> Unit
) {
    BackHandler(enabled = true) {
        onShowDialogChange(true)
    }

    if (showDialog) {
        PopupDialog(
            title = stringResource(R.string.dialog_check_exit),
            popupType = PopupType.QUIZ_EXIT,
            onCancelClick = { onShowDialogChange(false) },
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
            if (isLastQuiz) {
                quizViewModel.checkClearStage { clearType ->
                    onConfirmClick(clearType)
                }
            } else {
                onNextQuiz()
                onSelectedIndexChange(null)
                onUserInputChange("")
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        QuizTopBar(
            quizNumber = quizNumber,
            totalQuizCount = totalCount,
            onBackClick = { onShowDialogChange(true) }
        )

        Box(modifier = Modifier.fillMaxSize()) {
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

                if (currentQuiz.quizType !in listOf(
                        QuizType.TEXT_CHOICE_IMAGE,
                        QuizType.SHORT_ANSWER_IMAGE
                    )
                ) {
                    if (isCorrect != QuizResultType.DEFAULT) {
                        Spacer(modifier = Modifier.height(QuizResultCardSpacer))
                        QuizResultCard(isCorrect = isCorrect == QuizResultType.CORRECT)
                        Spacer(modifier = Modifier.height(QuizResultCardSpacer))
                    } else {
                        Spacer(modifier = Modifier.height(QuizQuestionTextSpacer))
                    }
                }

                when (currentQuiz.quizType) {
                    QuizType.IMAGE_CHOICE -> {
                        val imageChoices = currentQuiz.imageUrl?.mapIndexed { index, imageUrl ->
                            val label = currentQuiz.choices?.getOrNull(index) ?: ""
                            imageUrl to label
                        } ?: emptyList()

                        ImageQuizContent(
                            imgChoices = imageChoices,
                            answer = currentQuiz.answer,
                            selectedIndex = selectedIndex,
                            isCorrect = isCorrect,
                            onChoiceClick = { index ->
                                onSelectedIndexChange(index)
                                quizViewModel.checkAnswer(
                                    selectedAnswer = ('A' + index).toString()
                                )
                            }
                        )
                    }

                    QuizType.TEXT_CHOICE, QuizType.TEXT_CHOICE_IMAGE -> {
                        val textChoices = currentQuiz.choices ?: emptyList()

                        if (currentQuiz.quizType == QuizType.TEXT_CHOICE_IMAGE) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Spacer(
                                    modifier = Modifier.height(
                                        QuizResultCardTextChoiceImageSmallSpacer
                                    )
                                )

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
                                            modifier = Modifier.align(Alignment.TopCenter)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(QuizResultCardTextChoiceImageSpacer))
                            }
                        }

                        TextChoiceQuizContent(
                            textChoices = textChoices,
                            answer = currentQuiz.answer,
                            selectedIndex = selectedIndex,
                            isCorrect = isCorrect,
                            onChoiceClick = { index ->
                                onSelectedIndexChange(index)
                                quizViewModel.checkAnswer(
                                    selectedAnswer = ('A' + index).toString()
                                )
                            }
                        )
                    }

                    QuizType.SHORT_ANSWER, QuizType.SHORT_ANSWER_IMAGE -> {
                        if (currentQuiz.quizType == QuizType.SHORT_ANSWER_IMAGE) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Spacer(
                                    modifier = Modifier.height(
                                        QuizResultCardTextChoiceImageSmallSpacer
                                    )
                                )
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
                                            modifier = Modifier.align(Alignment.TopCenter)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(QuizResultCardTextChoiceImageSpacer))
                            }
                        }

                        ShortAnswerQuizContent(
                            quizType = currentQuiz.quizType,
                            userInput = userInput,
                            answer = currentQuiz.answer,
                            onUserInputChange = onUserInputChange,
                            onConfirmAnswer = {
                                quizViewModel.checkAnswer(
                                    selectedAnswer = userInput
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}