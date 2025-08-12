package com.paykidscompose.presentation.screen.login.nickname

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.base.UIEvent
import com.paykidscompose.presentation.model.LoginNicknameUIModel
import com.paykidscompose.presentation.ui.components.DecisionButton
import com.paykidscompose.presentation.ui.components.InfoText
import com.paykidscompose.presentation.ui.components.ScreenLoading
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
import kotlinx.coroutines.flow.collectLatest

// 스플래시 이후 로그인 화면입니다.
@Composable
fun Nickname(
    viewModel: LoginNicknameViewModel = viewModel(),
    onConfirmClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UIEvent.SuccessShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            when (it) {
                is PayKidsException.DialogException -> {}
                is PayKidsException.SnackBarException -> {}
                is PayKidsException.ToastException -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }

            viewModel.clearError()
        }
    }

    when {
        uiState.isLoading -> {
            ScreenLoading()
        }

        else -> {
            NicknameScreen(
                uiModel = uiState.uiModel,
                onSaveNickname = { viewModel.saveNickname() },
                onNicknameChange = { viewModel.updateNicknameInput(it) }
            )
        }
    }
}

@Composable
fun NicknameScreen(
    uiModel: LoginNicknameUIModel,
    onSaveNickname: () -> Unit,
    onNicknameChange: (String) -> Unit
) {

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

        NicknameInput(uiModel.nickname, onNicknameChange)

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            DecisionButton(
                stringResource(R.string.text_confirm_nickname),
                onClick = {
                    onSaveNickname()
                }
            )
        }
    }
}

@Composable
private fun NicknameInput(nickname: String, onNicknameChange: (String) -> Unit) {

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

@Preview(showSystemUi = true)
@Composable
fun NicknameScreenPreview() {
    PayKidsComposeTheme {
        Nickname { }
    }
}