package com.paykidscompose.presentation.screen.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.base.UIEvent
import com.paykidscompose.presentation.ui.components.InfoText
import com.paykidscompose.presentation.ui.components.ScreenLoading
import com.paykidscompose.presentation.ui.theme.Black
import com.paykidscompose.presentation.ui.theme.BottomButtonPadding
import com.paykidscompose.presentation.ui.theme.InfoTextStyle
import com.paykidscompose.presentation.ui.theme.LoginKakaoButtonTextStyle
import com.paykidscompose.presentation.ui.theme.LoginScreenKakaoButtonImageBottomPadding
import com.paykidscompose.presentation.ui.theme.LoginScreenKakaoButtonImageTopPadding
import com.paykidscompose.presentation.ui.theme.LoginScreenKakaoButtonStartPadding
import com.paykidscompose.presentation.ui.theme.LoginScreenKakaoTextBottomPadding
import com.paykidscompose.presentation.ui.theme.PayKidsComposeTheme
import com.paykidscompose.presentation.ui.theme.Shape5
import com.paykidscompose.presentation.ui.theme.StartAndEndPadding
import com.paykidscompose.presentation.ui.theme.Yellow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun Login(
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.flowWithLifecycle(lifecycleOwner.lifecycle).collectLatest { event ->
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
            LoginScreen(
                onKakaoClick = { viewModel.kakaoLogin() }
            )
        }
    }
}

@Composable
fun LoginScreen(onKakaoClick: () -> Unit = {}) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painterResource(R.drawable.bg_login),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = StartAndEndPadding,
                    end = StartAndEndPadding,
                    bottom = BottomButtonPadding
                ),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                InfoText(
                    text = stringResource(R.string.text_kakao_login),
                    style = InfoTextStyle
                )

                Spacer(modifier = Modifier.height(LoginScreenKakaoTextBottomPadding))

                KakaoButton(onKakaoClick)
            }
        }
    }
}

@Composable
fun KakaoButton(onKakaoClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = Shape5)
            .background(color = Yellow)
            .padding(start = LoginScreenKakaoButtonStartPadding)
            .clickable(onClick = {
                onKakaoClick()
            }),
        contentAlignment = Alignment.CenterStart
    ) {
        Image(
            painter = painterResource(R.drawable.ic_logo_provider_kakao),
            contentDescription = null,
            modifier = Modifier.padding(
                top = LoginScreenKakaoButtonImageTopPadding,
                bottom = LoginScreenKakaoButtonImageBottomPadding
            )
        )

        Text(
            stringResource(R.string.text_kakao_login_btn),
            style = LoginKakaoButtonTextStyle.copy(color = Black),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@Preview
@Composable
fun LoginScreenPreview() {
    PayKidsComposeTheme {
        LoginScreen()
    }
}