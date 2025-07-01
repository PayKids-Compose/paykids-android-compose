package com.paykidscompose.presentation.screens.mypage.section

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.ui.components.AppTopBar
import com.paykidscompose.presentation.ui.theme.Black2

@Composable
fun MyInfoTopBar(
    onBackClick: () -> Unit
) {
    AppTopBar(
        title = stringResource(R.string.text_my_page),
        showBackButton = true,
        onBackClick = onBackClick,
        titleColor = Black2
    )
}

@Composable
fun MyPageTopBar() {
    AppTopBar(stringResource(R.string.text_my_page))
}

@Composable
fun TermsPolicyTopBar(
    onBackClick: () -> Unit
) {
    AppTopBar(
        title = stringResource(R.string.text_terms_policy_title),
        showBackButton = true,
        onBackClick = onBackClick
    )
}