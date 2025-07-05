package com.paykidscompose.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.paykidscompose.presentation.ui.theme.Blue3
import com.paykidscompose.presentation.ui.theme.Gray5

@Composable
fun ScreenLoading(
    backgroundColor: Color = Gray5,
    indicatorColor: Color = Blue3,
) {
    Box(
        modifier = Modifier.fillMaxSize().background(color = backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = indicatorColor
        )
    }
}

@Composable
fun ScreenError(
    message: String = "재시도",
    onRetry: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize().background(Gray5),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = onRetry) {
            Text(message)
        }
    }
}