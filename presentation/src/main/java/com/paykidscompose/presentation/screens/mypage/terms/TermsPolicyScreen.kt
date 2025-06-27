package com.paykidscompose.presentation.screens.mypage.terms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.screens.PayKidsScaffold
import com.paykidscompose.presentation.ui.components.AppTopBar
import com.paykidscompose.presentation.ui.components.CardItem
import com.paykidscompose.presentation.ui.components.CustomCard
import com.paykidscompose.presentation.ui.theme.Gray5
import com.paykidscompose.presentation.ui.theme.Gray6
import com.paykidscompose.presentation.ui.theme.Gray8
import com.paykidscompose.presentation.ui.theme.TermsPolicyCardItemTitleTextStyle
import com.paykidscompose.presentation.ui.theme.TermsPolicyInfoTitleTextStyle
import com.paykidscompose.presentation.ui.theme.TermsScreenCardHeightSize
import com.paykidscompose.presentation.ui.theme.TermsScreenCardShapeTopBottom
import com.paykidscompose.presentation.ui.theme.TermsScreenSpacer16
import com.paykidscompose.presentation.ui.theme.TermsScreenStartEndPadding
import com.paykidscompose.presentation.ui.theme.TermsScreenTopPadding

@Composable
fun TermsPolicyScreen(
    onBackClick: () -> Unit = {}
) {
    PayKidsScaffold(
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
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = Gray5)
                .padding(
                    top = TermsScreenTopPadding,
                    start = TermsScreenStartEndPadding,
                    end = TermsScreenStartEndPadding
                )
        ) {
            Text(
                stringResource(R.string.text_require_consent),
                style = TermsPolicyInfoTitleTextStyle.copy(color = Gray8)
            )

            Spacer(modifier = Modifier.height(TermsScreenSpacer16))

            CustomCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(TermsScreenCardHeightSize),
                shapeTop = TermsScreenCardShapeTopBottom,
                shapeBottom = TermsScreenCardShapeTopBottom
            ) {
                CardItem(
                    stringResource(R.string.text_terms_of_use),
                    {},
                    titleTextStyle = TermsPolicyCardItemTitleTextStyle,
                    iconEnable = true
                )

                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(color = Gray6))

                CardItem(
                    stringResource(R.string.text_terms_policy_title),
                    {},
                    titleTextStyle = TermsPolicyCardItemTitleTextStyle,
                    iconEnable = true
                )

                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(color = Gray6))

                CardItem(
                    stringResource(R.string.text_license),
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