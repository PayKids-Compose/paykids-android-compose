package com.paykidscompose.presentation.screens.mypage.terms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.ui.components.AppTopBar
import com.paykidscompose.presentation.ui.components.CardItem
import com.paykidscompose.presentation.ui.components.CustomCard
import com.paykidscompose.presentation.ui.theme.Gray5
import com.paykidscompose.presentation.ui.theme.Gray8
import com.paykidscompose.presentation.ui.theme.TermsPolicyCardItemTitleTextStyle
import com.paykidscompose.presentation.ui.theme.TermsPolicyInfoTitleTextStyle

@Composable
fun TermsPolicyScreen(
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.text_terms_policy_title),
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
                    top = 36.dp,
                    start = 20.dp,
                    end = 20.dp
                )
        ) {
            Text(
                stringResource(R.string.text_require_consent),
                style = TermsPolicyInfoTitleTextStyle.copy(color = Gray8)
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                shapeTop = 10.dp,
                shapeBottom = 10.dp
            ) {
                CardItem(
                    "서비스 이용약관",
                    {},
                    titleTextStyle = TermsPolicyCardItemTitleTextStyle,
                    iconEnable = true
                )
                CardItem(
                    "약관 및 정책",
                    {},
                    titleTextStyle = TermsPolicyCardItemTitleTextStyle,
                    iconEnable = true
                )
                CardItem(
                    "오픈 라이선스",
                    {},
                    titleTextStyle = TermsPolicyCardItemTitleTextStyle,
                    iconEnable = true
                )
            }
        }
    }
}

@Preview
@Composable
fun TermsPolicyPreview() {
    TermsPolicyScreen()
}