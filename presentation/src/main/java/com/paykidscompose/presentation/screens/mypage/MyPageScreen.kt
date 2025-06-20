package com.paykidscompose.presentation.screens.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.dummy.DummyUser
import com.paykidscompose.presentation.ui.components.AppTopBar
import com.paykidscompose.presentation.ui.components.CustomCard
import com.paykidscompose.presentation.ui.components.InfoText
import com.paykidscompose.presentation.ui.components.TitleText
import com.paykidscompose.presentation.ui.theme.Black
import com.paykidscompose.presentation.ui.theme.CardItemBottomTopPadding
import com.paykidscompose.presentation.ui.theme.CardItemEndPadding
import com.paykidscompose.presentation.ui.theme.CardItemIconHeight
import com.paykidscompose.presentation.ui.theme.CardItemIconWidth
import com.paykidscompose.presentation.ui.theme.CardItemStartPadding
import com.paykidscompose.presentation.ui.theme.Gray5
import com.paykidscompose.presentation.ui.theme.Gray6
import com.paykidscompose.presentation.ui.theme.Gray7
import com.paykidscompose.presentation.ui.theme.MyPageAppBarTitleTextColor
import com.paykidscompose.presentation.ui.theme.MyPageCardTitleTextStyle
import com.paykidscompose.presentation.ui.theme.MyPageDefaultScreenBottomPadding
import com.paykidscompose.presentation.ui.theme.MyPageDefaultScreenImageSize
import com.paykidscompose.presentation.ui.theme.MyPageDefaultScreenSpacer20
import com.paykidscompose.presentation.ui.theme.MyPageDefaultScreenSpacer50
import com.paykidscompose.presentation.ui.theme.MyPageDefaultScreenSpacer8
import com.paykidscompose.presentation.ui.theme.MyPageDefaultScreenStartEndPadding
import com.paykidscompose.presentation.ui.theme.MyPageDefaultScreenTopPadding
import com.paykidscompose.presentation.ui.theme.MyPageNicknameTextStyle
import com.paykidscompose.presentation.ui.theme.Red

@Composable
fun MyPageScreen(
    onClickMyInfo: () -> Unit = {},
    onClickTerms: () -> Unit = {},
    onClickAppVersion: () -> Unit = {},
    onClickLogout: () -> Unit = {}
) {
    val user = DummyUser.getUsers().first()

    Scaffold(
        topBar = {
            AppTopBar(stringResource(R.string.text_my_page))
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = Gray5)
                .padding(
                    start = MyPageDefaultScreenStartEndPadding,
                    end = MyPageDefaultScreenStartEndPadding,
                    top = MyPageDefaultScreenTopPadding,
                    bottom = MyPageDefaultScreenBottomPadding
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(user.profileImageURL),
                contentDescription = null,
                modifier = Modifier
                    .size(MyPageDefaultScreenImageSize)
                    .clip(CircleShape)
            )

            Spacer(Modifier.height(MyPageDefaultScreenSpacer20))

            TitleText(
                user.nickname,
                color = MyPageAppBarTitleTextColor,
                style = MyPageNicknameTextStyle
            )

            Spacer(Modifier.height(MyPageDefaultScreenSpacer50))

            CustomCard {
                CardItem(
                    stringResource(R.string.text_my_info),
                    iconText = stringResource(R.string.text_modify_my_info),
                    iconEnable = true,
                    onItemClick = onClickMyInfo
                )
                HorizontalDivider(color = Gray6)
                CardItem(
                    stringResource(R.string.text_terms_policy_title),
                    iconEnable = true,
                    onItemClick = onClickTerms
                )
                HorizontalDivider(color = Gray6)
                CardItem(stringResource(R.string.text_app_version), onClickAppVersion)
                HorizontalDivider(color = Gray6)
                CardItem(stringResource(R.string.text_logout), textColor = Red, onItemClick = onClickLogout)
            }
        }
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
                    modifier = Modifier.size(width = CardItemIconWidth, height = CardItemIconHeight),
                    tint = Color.Unspecified,
                )
            }
        }

    }
}

@Preview
@Composable
fun MyPageScreenPreview() {
    MyPageScreen()
}