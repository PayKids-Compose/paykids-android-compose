package com.paykidscompose.presentation.screens.study

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.paykidscompose.presentation.model.ChatMessageUIModel
import com.paykidscompose.presentation.screens.study.section.ChatInput
import com.paykidscompose.presentation.screens.study.section.GptBubble
import com.paykidscompose.presentation.screens.study.section.StageInfoBox
import com.paykidscompose.presentation.screens.study.section.StudyTopBar
import com.paykidscompose.presentation.screens.study.section.UserBubble
import com.paykidscompose.presentation.ui.theme.Gray5
import com.paykidscompose.presentation.ui.theme.PayKidsComposeTheme
import com.paykidscompose.presentation.ui.theme.StudyChatBubbleSpacedBy
import com.paykidscompose.presentation.ui.theme.StudyChatColumnContentPadding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Study(
    onBackClick: () -> Unit = {}
) {
    val messages = remember {
        mutableStateListOf(
            ChatMessageUIModel("어린이 여러분 돈이란 무엇일까요?\n돈이 뭐냐면 사고 싶은 걸 살 수 있는 거랍니다", isFromGpt = true),
            ChatMessageUIModel("우와 그렇구나\n돈 없이는 사고 싶은 걸 가질 수 없나요?", isFromGpt = false),
        )
    }
    val stageNumber = "스테이지 3"
    val userNickname = "닉네임"
    var userInput by remember { mutableStateOf("") }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    StudyScreen(
        modifier = Modifier
            .fillMaxSize(),
        stageNumber = stageNumber,
        userNickname = userNickname,
        messages = messages,
        userInput = userInput,
        onBackClick = onBackClick,
        onUserInputChange = { userInput = it },
        onSendClick = {
            if (userInput.isNotBlank()) {
                messages.add(ChatMessageUIModel(userInput, isFromGpt = false))
                userInput = ""
                coroutineScope.launch {
                    delay(500)
                    messages.add(ChatMessageUIModel("GPT 응답 예시", isFromGpt = true))
                    listState.animateScrollToItem(messages.size - 1)
                }
            }
        },
        listState = listState
    )
}


@Composable
fun StudyScreen(
    modifier: Modifier = Modifier,
    stageNumber: String,
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
                    StageInfoBox(stage = stageNumber)
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

@Preview
@Composable
fun StudyPreview() {
    PayKidsComposeTheme {
        Study(
        )
    }
}