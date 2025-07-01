package com.paykidscompose.presentation.screens.study.section

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.ui.components.AppTopBar
import com.paykidscompose.presentation.ui.theme.Black
import com.paykidscompose.presentation.ui.theme.StudyAppBarTextStyle

@Composable
fun StudyTopBar(
    onBackClick: () -> Unit
) {
    AppTopBar(
        title = stringResource(R.string.text_study_title),
        titleStyle = StudyAppBarTextStyle,
        showBackButton = true,
        onBackClick = onBackClick,
        titleColor = Black,
        iconTint = Black
    )
}