package com.paykidscompose.presentation.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.ui.theme.Blue1
import com.paykidscompose.presentation.ui.theme.PayKidsComposeTheme
import com.paykidscompose.presentation.ui.theme.SplashLogo
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onTimeOut: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Blue1),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.paykids_logo_white),
            modifier = Modifier.size(SplashLogo),
            contentDescription = null
        )
    }

    LaunchedEffect(Unit) {
        delay(3000L)
        onTimeOut()
    }
}

@Preview
@Composable
fun SplashPreview() {
    PayKidsComposeTheme {
        SplashScreen {

        }
    }
}