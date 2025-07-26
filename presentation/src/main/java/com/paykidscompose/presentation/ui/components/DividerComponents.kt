package com.paykidscompose.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.paykidscompose.presentation.ui.theme.Gray6

// 제공되는 Divider 컴포저블은 버그 있는 거 같아서 대체 컴포넌트 구현함
@Composable
fun CustomDivider(
    height: Dp = 0.5.dp,
    backgroundColor: Color = Gray6
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(color = backgroundColor)
    )
}