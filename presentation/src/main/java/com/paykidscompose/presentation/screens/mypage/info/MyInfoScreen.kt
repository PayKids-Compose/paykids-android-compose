package com.paykidscompose.presentation.screens.mypage.info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.dummy.DummyUser
import com.paykidscompose.presentation.ui.components.AppTopBar
import com.paykidscompose.presentation.ui.components.CustomCard
import com.paykidscompose.presentation.ui.components.OutlineInputField
import com.paykidscompose.presentation.ui.components.TitleText
import com.paykidscompose.presentation.ui.theme.Black
import com.paykidscompose.presentation.ui.theme.Blue2
import com.paykidscompose.presentation.ui.theme.Gray5
import com.paykidscompose.presentation.ui.theme.MyInfoCardNicknameButtonTextStyle
import com.paykidscompose.presentation.ui.theme.MyInfoCardNicknameTextStyle
import com.paykidscompose.presentation.ui.theme.MyInfoCardTitleTextStyle
import com.paykidscompose.presentation.ui.theme.MyInfoCardUserDeleteTextStyle
import com.paykidscompose.presentation.ui.theme.MyInfoScreenAddIconSize
import com.paykidscompose.presentation.ui.theme.MyInfoScreenBoxSize
import com.paykidscompose.presentation.ui.theme.MyInfoScreenButtonHorizontalPadding
import com.paykidscompose.presentation.ui.theme.MyInfoScreenButtonShape
import com.paykidscompose.presentation.ui.theme.MyInfoScreenButtonVerticalPadding
import com.paykidscompose.presentation.ui.theme.MyInfoScreenEmailStartPadding
import com.paykidscompose.presentation.ui.theme.MyInfoScreenMyInfoStartEndPadding
import com.paykidscompose.presentation.ui.theme.MyInfoScreenMyInfoTopBottomPadding
import com.paykidscompose.presentation.ui.theme.MyInfoScreenNicknameStartPadding
import com.paykidscompose.presentation.ui.theme.MyInfoScreenProfileSize
import com.paykidscompose.presentation.ui.theme.MyInfoScreenShapeBottom
import com.paykidscompose.presentation.ui.theme.MyInfoScreenShapeTop
import com.paykidscompose.presentation.ui.theme.MyInfoScreenSpacer166
import com.paykidscompose.presentation.ui.theme.MyInfoScreenSpacer20
import com.paykidscompose.presentation.ui.theme.MyInfoScreenSpacer24
import com.paykidscompose.presentation.ui.theme.MyInfoScreenSpacer36
import com.paykidscompose.presentation.ui.theme.MyInfoScreenSpacer38
import com.paykidscompose.presentation.ui.theme.MyInfoScreenSpacer4
import com.paykidscompose.presentation.ui.theme.MyInfoScreenSpacer73
import com.paykidscompose.presentation.ui.theme.MyInfoScreenTopPadding
import com.paykidscompose.presentation.ui.theme.MyPageAppBarTitleTextColor
import com.paykidscompose.presentation.ui.theme.MyPageNicknameTextStyle
import com.paykidscompose.presentation.ui.theme.Red
import com.paykidscompose.presentation.ui.theme.White

@Composable
fun MyInfoScreen(
    onBackClick: () -> Unit = {}
) {
    val user = DummyUser.getUsers().first()

    var nickname by remember { mutableStateOf(user.nickname) }
    var email by remember { mutableStateOf(user.email) }

    val onNickname = { value: String ->
        nickname = value
    }

    val onEmail = { value: String ->
        email = value
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.text_my_page),
                showBackButton = true,
                onBackClick = onBackClick
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = Gray5)
                .padding(
                    top = MyInfoScreenTopPadding
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(MyInfoScreenBoxSize)
                    .clickable(onClick = {}), // 이미지 변경 클릭
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(user.profileImageURL),
                    contentDescription = null,
                    modifier = Modifier
                        .size(MyInfoScreenProfileSize)
                        .clip(CircleShape)
                )
                Icon(
                    painter = painterResource(R.drawable.ic_add_photo),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(MyInfoScreenAddIconSize)
                        .align(Alignment.BottomEnd)
                )
            }

            Spacer(modifier = Modifier.height(MyInfoScreenSpacer20))

            TitleText(
                nickname,
                color = MyPageAppBarTitleTextColor,
                style = MyPageNicknameTextStyle
            )

            Spacer(modifier = Modifier.height(MyInfoScreenSpacer73))

            CustomCard(
                Modifier.fillMaxSize(),
                shapeTop = MyInfoScreenShapeTop,
                shapeBottom = MyInfoScreenShapeBottom
            ) {
                MyInfoEdit(
                    nickname,
                    email,
                    onNickname,
                    onEmail
                )
            }
        }
    }
}

@Composable
fun MyInfoEdit(
    nickname: String,
    email: String,
    onNickname: (String) -> Unit,
    onEmail: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = MyInfoScreenMyInfoTopBottomPadding,
                bottom = MyInfoScreenMyInfoTopBottomPadding,
                start = MyInfoScreenMyInfoStartEndPadding,
                end = MyInfoScreenMyInfoStartEndPadding
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.text_my_info),
            style = MyInfoCardTitleTextStyle,
            color = Black
        )

        Spacer(modifier = Modifier.height(MyInfoScreenSpacer36))

        NicknameEdit(nickname, onNickname)

        Spacer(modifier = Modifier.height(MyInfoScreenSpacer20))

        EmailEdit(email, onEmail)

        Spacer(modifier = Modifier.height(MyInfoScreenSpacer166))

        Text(
            stringResource(R.string.text_withdrawal),
            modifier = Modifier.clickable(onClick = {}),
            style = MyInfoCardUserDeleteTextStyle,
            color = Red
        )
    }
}

@Composable
fun NicknameEdit(
    nickname: String,
    onNickname: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            stringResource(R.string.text_nickname),
            style = MyInfoCardNicknameTextStyle,
            color = Black
        )

        Spacer(modifier = Modifier.width(MyInfoScreenSpacer38))

        OutlineInputField(
            nickname,
            onNickname,
            hint = stringResource(R.string.text_nickname_hint),
            modifier = Modifier.weight(1f),
            startPadding = MyInfoScreenNicknameStartPadding
        )

        Spacer(modifier = Modifier.width(MyInfoScreenSpacer4))

        Button(
            onClick = {},
            shape = RoundedCornerShape(MyInfoScreenButtonShape),
            contentPadding = PaddingValues(
                horizontal = MyInfoScreenButtonHorizontalPadding,
                vertical = MyInfoScreenButtonVerticalPadding
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Blue2,
                contentColor = White
            )
        ) {
            Text(
                stringResource(R.string.text_confirm),
                style = MyInfoCardNicknameButtonTextStyle
            )
        }

    }
}

@Composable
fun EmailEdit(
    email: String,
    onEmail: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            stringResource(R.string.text_email),
            style = MyInfoCardNicknameTextStyle,
            color = Black
        )

        Spacer(modifier = Modifier.width(MyInfoScreenSpacer24))

        OutlineInputField(
            email,
            onEmail,
            hint = stringResource(R.string.text_email_hint),
            startPadding = MyInfoScreenEmailStartPadding
        )

    }
}

@Preview()
@Composable
fun MyInfoScreenPreview() {
    MyInfoScreen()
}