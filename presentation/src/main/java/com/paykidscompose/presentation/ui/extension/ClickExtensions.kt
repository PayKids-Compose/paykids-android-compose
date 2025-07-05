package com.paykidscompose.presentation.ui.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

private const val DEFAULT_INTERVAL_TIME = 300L

@Composable
fun rememberThrottledOnClick(
    intervalTime: Long = DEFAULT_INTERVAL_TIME,
    onClick: () -> Unit
): () -> Unit {
    var lastClickTime by remember { mutableLongStateOf(0L) }

    return {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > intervalTime) {
            lastClickTime = currentTime
            onClick()
        }
    }
}