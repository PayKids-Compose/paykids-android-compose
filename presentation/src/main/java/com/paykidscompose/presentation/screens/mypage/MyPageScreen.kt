package com.paykidscompose.presentation.screens.mypage

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.model.MyPageUIModel
import com.paykidscompose.presentation.screens.mypage.section.MyPageTopBar
import com.paykidscompose.presentation.ui.components.CardItem
import com.paykidscompose.presentation.ui.components.CustomCard
import com.paykidscompose.presentation.ui.components.CustomDivider
import com.paykidscompose.presentation.ui.components.PopupDialog
import com.paykidscompose.presentation.ui.components.ScreenLoading
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
fun MyPage(
    viewModel: MyPageViewModel = viewModel(),
    onClickMyInfo: () -> Unit = {},
    onClickTerms: () -> Unit = {},
    onClickAppVersion: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current

    if (uiState.showLogoutDialog) {
        PopupDialog(
            title = stringResource(R.string.dialog_signout_title),
            description = stringResource(R.string.dialog_signout_message),
            onCancelClick = { viewModel.dismissLogoutDialog() },
            onConfirmClick = { viewModel.confirmLogout() },
            popupType = PopupType.LOGOUT
        )
    }

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            when (it) {
                is PayKidsException.SnackBarException -> {

                }

                is PayKidsException.DialogException -> {

                }

                is PayKidsException.ToastException -> {

                }
            }
        }
    }

    when {
        uiState.isLoading -> {
            ScreenLoading()
        }

        uiState.myPage != null -> {
            MyPageScreen(
                uiModel = uiState.myPage!!,
                onClickMyInfo = onClickMyInfo,
                onClickTerms = onClickTerms,
                onClickAppVersion = onClickAppVersion,
                onLogoutClick = { viewModel.onLogoutClick() }
            )

        }
    }
}

@Composable
fun MyPageScreen(
    uiModel: MyPageUIModel,
    onClickMyInfo: () -> Unit = {},
    onClickTerms: () -> Unit = {},
    onClickAppVersion: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
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
            AsyncImage(
                model = uiModel.image,
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
                    onItemClick = onLogoutClick
                )
            }
        }
    }
}

@Preview
@Composable
fun MyPageScreenPreview() {
    MyPage()
}