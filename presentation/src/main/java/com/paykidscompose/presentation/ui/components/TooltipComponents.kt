package com.paykidscompose.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.ui.theme.Blue1
import com.paykidscompose.presentation.ui.theme.StageTooltipImageHeight
import com.paykidscompose.presentation.ui.theme.StageTooltipImageWidth
import com.paykidscompose.presentation.ui.theme.StageTooltipTextOffsetY
import com.paykidscompose.presentation.ui.theme.StageTooltipTextStyle

@Preview
@Composable
fun ImageTooltip(
    modifier: Modifier = Modifier,
    imageRes: Painter = painterResource(R.drawable.img_tooltip_blank),
    tooltipText: String = stringResource(R.string.text_tooltip_start)
) {
    Box(
        modifier = modifier
            .size(StageTooltipImageWidth, StageTooltipImageHeight),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.matchParentSize(),
            painter = imageRes,
            contentDescription = null,
        )
        Text(
            text = tooltipText,
            color = Blue1,
            style = StageTooltipTextStyle,
            modifier = Modifier.offset(y = StageTooltipTextOffsetY)
        )
    }
}