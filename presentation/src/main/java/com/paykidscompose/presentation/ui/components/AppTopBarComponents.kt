package com.paykidscompose.presentation.ui.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.ui.theme.MyPageAppBarTextStyle
import com.paykidscompose.presentation.ui.theme.MyPageAppBarTitleTextColor
import com.paykidscompose.presentation.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    showBackButton: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    painter: Painter = painterResource(R.drawable.ic_left_blue)
) {
    CenterAlignedTopAppBar(
        title = {
            TitleText(title, style = MyPageAppBarTextStyle, textAlign = TextAlign.Center)
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = White,
            titleContentColor = MyPageAppBarTitleTextColor
        ),
        navigationIcon = {
            if(showBackButton && onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painter,
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun AppBar(
) {
    AppTopBar("마이페이지", true, {})
}