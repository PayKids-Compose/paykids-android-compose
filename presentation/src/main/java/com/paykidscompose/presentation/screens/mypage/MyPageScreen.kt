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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.dummy.DummyUser
import com.paykidscompose.presentation.model.MyPageUIModel
import com.paykidscompose.presentation.screens.mypage.section.MyPageTopBar
import com.paykidscompose.presentation.ui.components.CardItem
import com.paykidscompose.presentation.ui.components.CustomCard
import com.paykidscompose.presentation.ui.components.CustomDivider
import com.paykidscompose.presentation.ui.components.PopupDialog
import com.paykidscompose.presentation.ui.components.TitleText
import com.paykidscompose.presentation.ui.components.util.PopupType
import com.paykidscompose.presentation.ui.theme.Black2
import com.paykidscompose.presentation.ui.theme.Gray5
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
    onClickAppVersion: () -> Unit = {}
) {
    val user = DummyUser.getUsers().first()

    val uiModel = MyPageUIModel(user.nickname, user.profileImageURL)

    var showPopupDialog by remember { mutableStateOf(false) }

    val onPopupDialog = {
        showPopupDialog = !showPopupDialog
    }

    if (showPopupDialog) {
        PopupDialog(
            title = stringResource(R.string.dialog_signout_title),
            description = stringResource(R.string.dialog_signout_message),
            onCancelClick = { showPopupDialog = false },
            onConfirmClick = {
                showPopupDialog = false
            },
            popupType = PopupType.LOGOUT
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ){
        MyPageTopBar()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Gray5)
                .padding(
                    start = MyPageDefaultScreenStartEndPadding,
                    end = MyPageDefaultScreenStartEndPadding,
                    top = MyPageDefaultScreenTopPadding
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(uiModel.image),
                contentDescription = null,
                modifier = Modifier
                    .size(MyPageDefaultScreenImageSize)
                    .clip(CircleShape)
            )

            Spacer(Modifier.height(MyPageDefaultScreenSpacer20))

            TitleText(
                uiModel.nickname,
                color = Black2,
                style = MyPageNicknameTextStyle
            )

            Spacer(Modifier.height(MyPageDefaultScreenSpacer50))

            CustomCard(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                CardItem(
                    stringResource(R.string.text_my_info),
                    iconText = stringResource(R.string.text_modify_my_info),
                    iconEnable = true,
                    onItemClick = onClickMyInfo
                )

                CustomDivider()

                CardItem(
                    stringResource(R.string.text_terms_policy_title),
                    iconEnable = true,
                    onItemClick = onClickTerms
                )

                CustomDivider()

                CardItem(stringResource(R.string.text_app_version), onClickAppVersion)

                CustomDivider()

                CardItem(
                    stringResource(R.string.text_logout),
                    textColor = Red,
                    onItemClick = onPopupDialog
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