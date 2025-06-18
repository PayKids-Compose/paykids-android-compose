package com.paykidscompose.presentation.screens.login.nickname

import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.ui.components.DefaultButton
import com.paykidscompose.presentation.ui.components.InputField
import com.paykidscompose.presentation.ui.components.TitleText
import com.paykidscompose.presentation.ui.theme.Blue2
import com.paykidscompose.presentation.ui.theme.Dimens.Dp12
import com.paykidscompose.presentation.ui.theme.Dimens.Dp200
import com.paykidscompose.presentation.ui.theme.Dimens.Dp24
import com.paykidscompose.presentation.ui.theme.Dimens.Dp32
import com.paykidscompose.presentation.ui.theme.Dimens.Dp52
import com.paykidscompose.presentation.ui.theme.Dimens.Dp8
import com.paykidscompose.presentation.ui.theme.Gray5
import com.paykidscompose.presentation.ui.theme.PayKidsComposeTheme
import com.paykidscompose.presentation.ui.theme.typography

// 스플래시 이후 로그인 화면입니다.

@Composable
fun NicknameScreen(
    onConfirmClick: () -> Unit
) {
    var nickname by remember { mutableStateOf("") }
    val onNicknameChange = { value: String ->
        nickname = value
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray5)
            .padding(Dp24).padding(bottom = Dp32)

    ) {
        Spacer(modifier = Modifier.weight(0.4f))

        TitleText(
            stringResource(R.string.text_set_nickname)
        )

        Spacer(modifier = Modifier.height(Dp24))

        NicknameInput(nickname, onNicknameChange)

        Spacer(modifier = Modifier.weight(1f))

        DefaultButton(
            stringResource(R.string.text_confirm_nickname),
            onConfirmClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(Dp52)
                .clip(RoundedCornerShape(Dp24))
        )
    }
}

@Composable
fun NicknameInput(nickname: String, onNicknameChange: (String) -> Unit) {
    InputField(
        nickname,
        onNicknameChange,
        hint = stringResource(R.string.hint_set_nickname)
    )

    Spacer(modifier = Modifier.height(Dp8))

    if(nickname.isNotEmpty()) {
        Text(
            text = stringResource(R.string.text_validate_nickname),
            style = typography.bodySmall,
            color = Blue2
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