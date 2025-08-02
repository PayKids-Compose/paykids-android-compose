package com.paykidscompose.presentation.screens.mypage

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.base.UIEvent
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
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MyPage(
    viewModel: MyPageViewModel = viewModel(),
    onClickMyInfo: () -> Unit = {},
    onClickTerms: () -> Unit = {},
    onClickAppVersion: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UIEvent.SuccessShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            when (it) {
                is PayKidsException.SnackBarException -> {

                }

                is PayKidsException.DialogException -> {
                }

                is PayKidsException.ToastException -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }

            viewModel.clearError()
        }
    }

    when {
        uiState.isLoading -> {
            ScreenLoading()
        }

        uiState.uiModel != null -> {
            MyPageScreen(
                uiModel = uiState.uiModel!!,
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
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
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

            Spacer(Modifier.height(MyPageDefaultScreenSpacer50))

        }
    }
}

@Preview
@Composable
fun MyPageScreenPreview() {
    MyPage()
}