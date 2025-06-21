package com.paykidscompose.presentation.screens.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.dummy.DummyUser
import com.paykidscompose.presentation.ui.components.AppTopBar
import com.paykidscompose.presentation.ui.components.CardItem
import com.paykidscompose.presentation.ui.components.CustomCard
import com.paykidscompose.presentation.ui.components.TitleText
import com.paykidscompose.presentation.ui.theme.CustomCardSizeHeight
import com.paykidscompose.presentation.ui.theme.Gray5
import com.paykidscompose.presentation.ui.theme.Gray6
import com.paykidscompose.presentation.ui.theme.MyPageAppBarTitleTextColor
import com.paykidscompose.presentation.ui.theme.MyPageDefaultScreenBottomPadding
import com.paykidscompose.presentation.ui.theme.MyPageDefaultScreenImageSize
import com.paykidscompose.presentation.ui.theme.MyPageDefaultScreenSpacer20
import com.paykidscompose.presentation.ui.theme.MyPageDefaultScreenSpacer50
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

            CustomCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CustomCardSizeHeight)
            ) {
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
                CardItem(
                    stringResource(R.string.text_logout),
                    textColor = Red,
                    onItemClick = onClickLogout
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