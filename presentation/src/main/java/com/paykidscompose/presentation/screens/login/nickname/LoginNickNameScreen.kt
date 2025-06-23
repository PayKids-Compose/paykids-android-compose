package com.paykidscompose.presentation.screens.login.nickname

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.dummy.DummyUser
import com.paykidscompose.presentation.ui.components.DecisionButton
import com.paykidscompose.presentation.ui.components.InfoText
import com.paykidscompose.presentation.ui.components.TitleText
import com.paykidscompose.presentation.ui.components.UnderlineInputField
import com.paykidscompose.presentation.ui.theme.Blue2
import com.paykidscompose.presentation.ui.theme.DeterminationButtonPadding
import com.paykidscompose.presentation.ui.theme.FieldAndInfoSpacer
import com.paykidscompose.presentation.ui.theme.Gray5
import com.paykidscompose.presentation.ui.theme.NicknameScreenTopPadding
import com.paykidscompose.presentation.ui.theme.PayKidsComposeTheme
import com.paykidscompose.presentation.ui.theme.StartAndEndPadding
import com.paykidscompose.presentation.ui.theme.TitleAndFieldSpacer

// 스플래시 이후 로그인 화면입니다.
@Composable
fun NicknameScreen(
    onConfirmClick: () -> Unit
) {
    var nickname by remember { mutableStateOf("") }
    val onNicknameChange = { value: String ->
        nickname = value
    }
    val user = DummyUser.getUsers().first()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray5)
            .padding(
                start = StartAndEndPadding,
                end = StartAndEndPadding,
                top = NicknameScreenTopPadding,
                bottom = DeterminationButtonPadding
            )
    ) {
        TitleText(
            stringResource(R.string.text_set_nickname),
            Modifier.fillMaxWidth()
        )

        NicknameInput(nickname, onNicknameChange)

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            DecisionButton(
                stringResource(R.string.text_confirm_nickname),
                onClick = {
                    user.nickname = nickname
                    DummyUser.setUser(user)
                    onConfirmClick()
                }
            )
        }
    }
}

@Composable
fun NicknameInput(nickname: String, onNicknameChange: (String) -> Unit) {

    Spacer(modifier = Modifier.height(TitleAndFieldSpacer))

    UnderlineInputField(
        nickname,
        onNicknameChange,
        hint = stringResource(R.string.hint_set_nickname),
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(FieldAndInfoSpacer))

    if (nickname.isNotEmpty()) {
        InfoText(
            text = stringResource(R.string.text_validate_nickname),
            textColor = Blue2
        )
    }
}

@Preview
@Composable
fun NicknameScreenPreview() {
    PayKidsComposeTheme {
        NicknameScreen { }
    }
}