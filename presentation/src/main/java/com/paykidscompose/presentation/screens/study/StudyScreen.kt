package com.paykidscompose.presentation.screens.study

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.model.study.ChatMessageUIModel
import com.paykidscompose.presentation.screens.study.section.StudyTopBar
import com.paykidscompose.presentation.ui.components.OutlineInputField
import com.paykidscompose.presentation.ui.components.ScreenLoading
import com.paykidscompose.presentation.ui.theme.Blue1
import com.paykidscompose.presentation.ui.theme.Gray1
import com.paykidscompose.presentation.ui.theme.Gray5
import com.paykidscompose.presentation.ui.theme.Gray6
import com.paykidscompose.presentation.ui.theme.Gray7
import com.paykidscompose.presentation.ui.theme.PayKidsComposeTheme
import com.paykidscompose.presentation.ui.theme.StudyChatBubbleHorizontalPadding
import com.paykidscompose.presentation.ui.theme.StudyChatBubbleNicknameSpacer
import com.paykidscompose.presentation.ui.theme.StudyChatBubbleSpacedBy
import com.paykidscompose.presentation.ui.theme.StudyChatBubbleTextStyle
import com.paykidscompose.presentation.ui.theme.StudyChatBubbleVerticalPadding
import com.paykidscompose.presentation.ui.theme.StudyChatColumnContentPadding
import com.paykidscompose.presentation.ui.theme.StudyChatGptBubbleRoundBottomEnd
import com.paykidscompose.presentation.ui.theme.StudyChatGptBubbleRoundBottomStart
import com.paykidscompose.presentation.ui.theme.StudyChatGptBubbleRoundTopEnd
import com.paykidscompose.presentation.ui.theme.StudyChatGptBubbleRoundTopStart
import com.paykidscompose.presentation.ui.theme.StudyChatInputBoxHorizontalPadding
import com.paykidscompose.presentation.ui.theme.StudyChatInputBoxVerticalPadding
import com.paykidscompose.presentation.ui.theme.StudyChatInputFieldHeight
import com.paykidscompose.presentation.ui.theme.StudyChatInputFieldRound
import com.paykidscompose.presentation.ui.theme.StudyChatInputFieldStartPadding
import com.paykidscompose.presentation.ui.theme.StudyChatInputRoundTopEnd
import com.paykidscompose.presentation.ui.theme.StudyChatInputRoundTopStart
import com.paykidscompose.presentation.ui.theme.StudyChatInputShadowElevation
import com.paykidscompose.presentation.ui.theme.StudyChatInputTextStyle
import com.paykidscompose.presentation.ui.theme.StudyChatNicknameTextStyle
import com.paykidscompose.presentation.ui.theme.StudyChatSendIconSize
import com.paykidscompose.presentation.ui.theme.StudyChatUserBubbleBorderWidth
import com.paykidscompose.presentation.ui.theme.StudyChatUserBubbleRoundBottomEnd
import com.paykidscompose.presentation.ui.theme.StudyChatUserBubbleRoundBottomStart
import com.paykidscompose.presentation.ui.theme.StudyChatUserBubbleRoundTopEnd
import com.paykidscompose.presentation.ui.theme.StudyChatUserBubbleRoundTopStart
import com.paykidscompose.presentation.ui.theme.StudyStageNumberBoxBorderWidth
import com.paykidscompose.presentation.ui.theme.StudyStageNumberBoxHorizontalPadding
import com.paykidscompose.presentation.ui.theme.StudyStageNumberBoxRound
import com.paykidscompose.presentation.ui.theme.StudyStageNumberBoxTextStyle
import com.paykidscompose.presentation.ui.theme.StudyStageNumberBoxVerticalPadding
import com.paykidscompose.presentation.ui.theme.White
import com.paykidscompose.presentation.ui.theme.White4

@Composable
fun Study(
    stageNumber: Int,
    studyViewModel: StudyViewModel = viewModel(),
    onBackClick: () -> Unit = {}
) {
    val uiState by studyViewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val stageNumberText = stringResource(R.string.text_stage_number, stageNumber)
    val userNickname = uiState.userNickname

    val listState = rememberLazyListState()

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

            studyViewModel.clearError()
        }
    }

    when {
        uiState.isLoading -> {
            ScreenLoading()
        }
        else -> {
            StudyScreen(
                modifier = Modifier
                    .fillMaxSize(),
                stageNumberText = stageNumberText,
                userNickname = userNickname,
                messages = uiState.messages,
                userInput = uiState.userInput,
                onBackClick = onBackClick,
                onUserInputChange = { text -> studyViewModel.onUserInputChange(text) },
                onSendClick = {
                    studyViewModel.sendUserInput()
                },
                listState = listState
            )
        }
    }
}


@Composable
fun StudyScreen(
    modifier: Modifier = Modifier,
    stageNumberText: String,
    userNickname: String,
    messages: List<ChatMessageUIModel>,
    userInput: String,
    onBackClick: () -> Unit,
    onUserInputChange: (String) -> Unit,
    onSendClick: () -> Unit,
    listState: LazyListState
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Gray5)
    ) {
        StudyTopBar(onBackClick = onBackClick)
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            state = listState,
            contentPadding = PaddingValues(StudyChatColumnContentPadding),
            verticalArrangement = Arrangement.spacedBy(StudyChatBubbleSpacedBy)
        ) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                ) {
                    StageInfoBox(stage = stageNumberText)
                }
            }

            items(messages) { message ->
                if (message.isFromGpt) {
                    GptBubble(message.text)
                } else {
                    UserBubble(userNickname, message.text)
                }
            }
        }

        ChatInput(
            text = userInput,
            onTextChanged = onUserInputChange,
            onSendClick = onSendClick
        )
    }
}

@Composable
fun StageInfoBox(stage: String) {
    Box(
        modifier = Modifier
            .background(White, shape = RoundedCornerShape(StudyStageNumberBoxRound))
            .border(
                width = StudyStageNumberBoxBorderWidth,
                color = Gray7,
                shape = RoundedCornerShape(StudyStageNumberBoxRound)
            )
            .padding(
                horizontal = StudyStageNumberBoxHorizontalPadding,
                vertical = StudyStageNumberBoxVerticalPadding
            )
    ) {
        Text(
            text = stage, style = StudyStageNumberBoxTextStyle, color = Gray7
        )
    }
}

@Composable
fun GptBubble(text: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.text_chatgpt),
            style = StudyChatNicknameTextStyle.copy(Blue1)
        )
        Spacer(modifier = Modifier.height(StudyChatBubbleNicknameSpacer))
        Box(
            modifier = Modifier
                .background(
                    Blue1, shape = RoundedCornerShape(
                        StudyChatGptBubbleRoundTopStart,
                        StudyChatGptBubbleRoundTopEnd,
                        StudyChatGptBubbleRoundBottomEnd,
                        StudyChatGptBubbleRoundBottomStart
                    )
                )
                .padding(
                    horizontal = StudyChatBubbleHorizontalPadding,
                    vertical = StudyChatBubbleVerticalPadding
                )
                .align(Alignment.Start)
        ) {
            Text(text, style = StudyChatBubbleTextStyle.copy(color = White))
        }
    }
}

@Composable
fun UserBubble(userNickname: String, text: String) {
    Column(
        modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End
    ) {
        Text(text = userNickname, style = StudyChatNicknameTextStyle.copy(color = Gray1))
        Spacer(modifier = Modifier.height(StudyChatBubbleNicknameSpacer))
        Box(
            modifier = Modifier
                .background(
                    White, RoundedCornerShape(
                        StudyChatUserBubbleRoundTopStart,
                        StudyChatUserBubbleRoundTopEnd,
                        StudyChatUserBubbleRoundBottomEnd,
                        StudyChatUserBubbleRoundBottomStart
                    )
                )
                .border(
                    StudyChatUserBubbleBorderWidth, Gray6, RoundedCornerShape(
                        StudyChatUserBubbleRoundTopStart,
                        StudyChatUserBubbleRoundTopEnd,
                        StudyChatUserBubbleRoundBottomEnd,
                        StudyChatUserBubbleRoundBottomStart
                    )
                )
                .padding(
                    horizontal = StudyChatBubbleHorizontalPadding,
                    vertical = StudyChatBubbleVerticalPadding
                )
        ) {
            Text(text, style = StudyChatBubbleTextStyle, textAlign = TextAlign.End)
        }
    }
}

@Composable
fun ChatInput(
    text: String, onTextChanged: (String) -> Unit, onSendClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = StudyChatInputShadowElevation,
                shape = RoundedCornerShape(
                    StudyChatInputRoundTopStart, StudyChatInputRoundTopEnd
                )
            ),
        shape = RoundedCornerShape(
            StudyChatInputRoundTopStart, StudyChatInputRoundTopEnd
        ),
        colors = CardDefaults.cardColors(containerColor = White4)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = StudyChatInputBoxHorizontalPadding,
                    vertical = StudyChatInputBoxVerticalPadding
                ), contentAlignment = Alignment.Center
        ) {
            OutlineInputField(
                text = text,
                style = StudyChatInputTextStyle,
                onTextChange = onTextChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(StudyChatInputFieldHeight),
                hint = stringResource(R.string.hint_chat),
                startPadding = StudyChatInputFieldStartPadding,
                shape = RoundedCornerShape(StudyChatInputFieldRound),
                backgroundColor = White
            )
            IconButton(
                onClick = onSendClick,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_send),
                    contentDescription = null,
                    modifier = Modifier.size(StudyChatSendIconSize)
                )
            }
        }
    }
}

@Preview
@Composable
fun GptBubblePreview() {
    PayKidsComposeTheme {
        GptBubble("안녕하세요")
    }
}

@Preview
@Composable
fun UserBubblePreview() {
    PayKidsComposeTheme {
        UserBubble("닉네임", "안녕")
    }
}

@Preview
@Composable
fun StudyScreenPreview() {
    PayKidsComposeTheme {
        Study(stageNumber = 3)
    }
}