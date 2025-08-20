package com.paykidscompose.presentation.screen.study

import android.widget.Toast
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.model.study.ChatMessageUIModel
import com.paykidscompose.presentation.screen.study.section.StudyTopBar
import com.paykidscompose.presentation.ui.components.OutlineInputField
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
    studyViewModel: StudyViewModel = hiltViewModel(),
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

    StudyScreen(
        modifier = Modifier
            .fillMaxSize(),
        stageNumberText = stageNumberText,
        userNickname = userNickname,
        messages = uiState.messages,
        userInput = uiState.userInput,
        isLoading = uiState.isLoading,
        onBackClick = onBackClick,
        onUserInputChange = { text -> studyViewModel.onUserInputChange(text) },
        onSendClick = {
            studyViewModel.sendUserInput()
        },
        listState = listState
    )

}

@Composable
fun StudyScreen(
    modifier: Modifier = Modifier,
    stageNumberText: String,
    userNickname: String,
    messages: List<ChatMessageUIModel>,
    userInput: String,
    isLoading: Boolean,
    onBackClick: () -> Unit,
    onUserInputChange: (String) -> Unit,
    onSendClick: () -> Unit,
    listState: LazyListState
) {
    // 메시지가 추가될 때 자동으로 맨 아래로 스크롤
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }

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
                    GptBubble(message.text, isLoading)
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
fun GptBubble(text: String, isLoading: Boolean) {
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
            if (isLoading && text.isBlank()) {
                TypingDots()
            } else {
                Text(text, style = StudyChatBubbleTextStyle.copy(color = White))
            }
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

@Composable
fun TypingDots() {
    val dotCount = 3
    val transition = rememberInfiniteTransition()

    val alphaValues = List(dotCount) { index ->
        transition.animateFloat(
            initialValue = 0.3f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 500, delayMillis = index * 200),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Row {
        repeat(dotCount) { index ->
            Text(
                text = stringResource(R.string.typing_dot),
                style = StudyChatBubbleTextStyle.copy(color = White),
                modifier = Modifier.alpha(alphaValues[index].value)
            )
        }
    }
}

@Preview
@Composable
fun GptBubblePreview() {
    PayKidsComposeTheme {
        GptBubble("안녕하세요", false)
    }
}

@Preview
@Composable
fun GptBubbleLoadingPreview() {
    PayKidsComposeTheme {
        GptBubble("", true)
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