package com.paykidscompose.presentation.screens.study.section

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.ui.theme.Blue1
import com.paykidscompose.presentation.ui.theme.Gray1
import com.paykidscompose.presentation.ui.theme.Gray6
import com.paykidscompose.presentation.ui.theme.PayKidsComposeTheme
import com.paykidscompose.presentation.ui.theme.StudyChatBubbleHorizontalPadding
import com.paykidscompose.presentation.ui.theme.StudyChatBubbleNicknameSpacer
import com.paykidscompose.presentation.ui.theme.StudyChatBubbleTextStyle
import com.paykidscompose.presentation.ui.theme.StudyChatBubbleVerticalPadding
import com.paykidscompose.presentation.ui.theme.StudyChatGptBubbleRoundBottomEnd
import com.paykidscompose.presentation.ui.theme.StudyChatGptBubbleRoundBottomStart
import com.paykidscompose.presentation.ui.theme.StudyChatGptBubbleRoundTopEnd
import com.paykidscompose.presentation.ui.theme.StudyChatGptBubbleRoundTopStart
import com.paykidscompose.presentation.ui.theme.StudyChatNicknameTextStyle
import com.paykidscompose.presentation.ui.theme.StudyChatUserBubbleBorderWidth
import com.paykidscompose.presentation.ui.theme.StudyChatUserBubbleRoundBottomEnd
import com.paykidscompose.presentation.ui.theme.StudyChatUserBubbleRoundBottomStart
import com.paykidscompose.presentation.ui.theme.StudyChatUserBubbleRoundTopEnd
import com.paykidscompose.presentation.ui.theme.StudyChatUserBubbleRoundTopStart
import com.paykidscompose.presentation.ui.theme.White

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