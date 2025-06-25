package com.paykidscompose.presentation.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.paykidscompose.presentation.ui.theme.Blue2
import com.paykidscompose.presentation.ui.theme.DeterminationButtonCorner
import com.paykidscompose.presentation.ui.theme.DeterminationButtonTextTopAndBottom
import com.paykidscompose.presentation.ui.theme.DeterminationTextStyle
import com.paykidscompose.presentation.ui.theme.Gray2
import com.paykidscompose.presentation.ui.theme.White

@Composable
fun DecisionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Blue2,
    contentColor: Color = White,
    disabledBackgroundColor: Color = Gray2,
    disabledContentColor: Color = White,
    enabled: Boolean = true,
    textStyle: TextStyle = DeterminationTextStyle,
    contentPadding: Dp = DeterminationButtonTextTopAndBottom,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(DeterminationButtonCorner)),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = disabledBackgroundColor,
            disabledContentColor = disabledContentColor
        ),
        contentPadding = PaddingValues(vertical = contentPadding)
    ) {
        Text(
            text = text,
            style = textStyle
        )
    }
}

@Preview
@Composable
fun ButtonPreview() {
    DecisionButton("결정하기", {})
}