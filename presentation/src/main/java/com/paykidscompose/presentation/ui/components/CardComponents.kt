package com.paykidscompose.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.ui.theme.Black
import com.paykidscompose.presentation.ui.theme.CardItemBottomTopPadding
import com.paykidscompose.presentation.ui.theme.CardItemEndPadding
import com.paykidscompose.presentation.ui.theme.CardItemIconHeight
import com.paykidscompose.presentation.ui.theme.CardItemIconWidth
import com.paykidscompose.presentation.ui.theme.CardItemStartPadding
import com.paykidscompose.presentation.ui.theme.CustomCardShadow
import com.paykidscompose.presentation.ui.theme.Gray7
import com.paykidscompose.presentation.ui.theme.MyPageCardShadowColor
import com.paykidscompose.presentation.ui.theme.MyPageCardTitleTextStyle
import com.paykidscompose.presentation.ui.theme.MyPageDefaultScreenSpacer8
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
            .background(
                White, shape = shape
            )
    ) {
        Column(content = content)
    }
}


@Composable
fun CardItem(
    title: String,
    onItemClick: () -> Unit,
    textColor: Color = Black,
    titleTextStyle: TextStyle = MyPageCardTitleTextStyle,
    iconText: String = "",
    iconEnable: Boolean = false,

    ) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick)
            .padding(
                start = CardItemStartPadding,
                bottom = CardItemBottomTopPadding,
                end = CardItemEndPadding,
                top = CardItemBottomTopPadding
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(title, style = titleTextStyle, color = textColor)

        if (iconEnable) {
            Row(
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                InfoText(iconText, textColor = Gray7)

                Spacer(modifier = Modifier.width(MyPageDefaultScreenSpacer8))

                Icon(
                    painter = painterResource(R.drawable.ic_right),
                    contentDescription = null,
                    modifier = Modifier.size(
                        width = CardItemIconWidth,
                        height = CardItemIconHeight
                    ),
                    tint = Color.Unspecified,
                )
            }
        }

    }
}

@Preview
@Composable
fun CardPreview() {
    PayKidsComposeTheme {
        CustomCard { }
    }
}