package com.paykidscompose.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.paykidscompose.presentation.ui.theme.CustomCardShadow
import com.paykidscompose.presentation.ui.theme.CustomCardSizeHeight
import com.paykidscompose.presentation.ui.theme.CustomCardSizeWidth
import com.paykidscompose.presentation.ui.theme.MyPageCardShadowColor
import com.paykidscompose.presentation.ui.theme.PayKidsComposeTheme
import com.paykidscompose.presentation.ui.theme.White

@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    shadowColor: Color = MyPageCardShadowColor,
    shapeTop: Dp = CustomCardShadow,
    shapeBottom: Dp = CustomCardShadow,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = RoundedCornerShape(
        topStart = shapeTop,
        topEnd = shapeTop,
        bottomStart = shapeBottom,
        bottomEnd = shapeBottom
    )

    Box(
        modifier = modifier
            .shadow(
                elevation = CustomCardShadow,
                shape = shape,
                ambientColor = shadowColor,
                spotColor = shadowColor
            )
            .size(CustomCardSizeWidth, CustomCardSizeHeight)
            .background(
                White, shape = shape
            )
    ) {
        Column(content = content)
    }
}

@Preview
@Composable
fun CardPreview() {
    PayKidsComposeTheme {
        CustomCard { }
    }
}