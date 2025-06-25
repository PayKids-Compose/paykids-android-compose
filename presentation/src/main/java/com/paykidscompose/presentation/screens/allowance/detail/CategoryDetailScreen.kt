package com.paykidscompose.presentation.screens.allowance.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.screens.PayKidsScaffold
import com.paykidscompose.presentation.ui.components.AllowanceInputDialog
import com.paykidscompose.presentation.ui.theme.Black
import com.paykidscompose.presentation.ui.theme.Blue1
import com.paykidscompose.presentation.ui.theme.CategoryDetailItemAddTextStyle
import com.paykidscompose.presentation.ui.theme.CategoryDetailItemAmountTextStyle
import com.paykidscompose.presentation.ui.theme.CategoryDetailItemCategoryTextStyle
import com.paykidscompose.presentation.ui.theme.CategoryDetailItemMemoTextStyle
import com.paykidscompose.presentation.ui.theme.CategoryDetailScreenItemStartEndPadding
import com.paykidscompose.presentation.ui.theme.CategoryDetailScreenItemTopBottomPadding
import com.paykidscompose.presentation.ui.theme.CategoryDetailScreenSpacer24
import com.paykidscompose.presentation.ui.theme.CategoryDetailScreenSpacer6
import com.paykidscompose.presentation.ui.theme.CategoryDetailScreenSpacer8
import com.paykidscompose.presentation.ui.theme.CategoryDetailScreenStartEndPadding
import com.paykidscompose.presentation.ui.theme.CategoryDetailScreenTopPadding
import com.paykidscompose.presentation.ui.theme.CategoryDetailTitleTextStyle
import com.paykidscompose.presentation.ui.theme.Gray1
import com.paykidscompose.presentation.ui.theme.Gray5
import com.paykidscompose.presentation.ui.theme.Gray7
import com.paykidscompose.presentation.ui.theme.MyPageCardShadowColor
import com.paykidscompose.presentation.ui.theme.ShadowElevation16
import com.paykidscompose.presentation.ui.theme.Shape10
import com.paykidscompose.presentation.ui.theme.White
import com.paykidscompose.presentation.ui.theme.White2
import com.paykidscompose.presentation.util.formatAmount

data class DetailTestModel(
    val category: String,
    val amount: Int,
    val memo: String
)

@Composable
fun CategoryDetail(

) {
    val category = "편의점" // uimodel로 다 바꿀겁니다. 지금은 정적인 화면 구현 중입니다.
    val amount = 150000

    val details = listOf(
        DetailTestModel("편의점", 1800, "불닭 사먹음 ㅎㅎ"),
        DetailTestModel("편의점", 4000, "도시락 사먹음 ㅎㅎ"),
        DetailTestModel("편의점", 2000, "음료수 사먹음 ㅎㅎ")
    )

    var showDialog by remember { mutableStateOf(false) }

    val onDialog = { value: Boolean ->
        showDialog = value
    }

    CategoryDetailScreen(
        category = category,
        amount = amount,
        details = details,
        showDialog = showDialog,
        onDialog = onDialog
    )
}

@Composable
fun CategoryDetailScreen(
    category: String,
    amount: Int,
    showDialog: Boolean,
    onDialog: (Boolean) -> Unit,
    details: List<DetailTestModel>
) {
    if (showDialog) {
        AllowanceInputDialog(
            onSelect = {},
            onCancelClick = {
                onDialog(!showDialog)
            },
            onConfirmClick = {
                onDialog(!showDialog)
            }
        )
    }

    PayKidsScaffold(
        bottomBar = {

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Gray5)
                .statusBarsPadding()
                .padding(innerPadding)
                .padding(
                    start = CategoryDetailScreenStartEndPadding,
                    end = CategoryDetailScreenStartEndPadding,
                    top = CategoryDetailScreenTopPadding
                )
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(SpanStyle(color = Blue1)) {
                        append(category)
                    }
                    withStyle(SpanStyle(color = Black)) {
                        append(
                            stringResource(
                                R.string.text_category_consume,
                                formatAmount(amount)
                            )
                        )
                    }
                },
                style = CategoryDetailTitleTextStyle
            )

            Spacer(Modifier.height(CategoryDetailScreenSpacer24))

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(details) {
                    DetailItem(it, showDialog, onDialog)

                    Spacer(Modifier.height(CategoryDetailScreenSpacer8))
                }

                item {
                    // 나중에 재사용할 수 있게 만들어야 할 거 같습니다.
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(Shape10)
                            .clickable(
                                onClick = {
                                    // 추가하기 로직 추가 예정
                                }
                            )
                            .background(color = White2)
                            .shadow(
                                elevation = ShadowElevation16,
                                shape = Shape10,
                                ambientColor = MyPageCardShadowColor,
                                spotColor = MyPageCardShadowColor
                            )
                            .padding(
                                top = CategoryDetailScreenItemTopBottomPadding,
                                bottom = CategoryDetailScreenItemTopBottomPadding
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            stringResource(R.string.text_add_category),
                            style = CategoryDetailItemAddTextStyle.copy(color = Gray7)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DetailItem(
    data: DetailTestModel,
    showDialog: Boolean,
    onDialog: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(Shape10)
            .clickable(
                onClick = {
                    // 수정하는 화면으로 이동 로직 추가 예정
                    onDialog(!showDialog)
                }
            )
            .background(color = White)
            .shadow(
                elevation = ShadowElevation16,
                shape = Shape10,
                ambientColor = MyPageCardShadowColor,
                spotColor = MyPageCardShadowColor
            )
            .padding(
                start = CategoryDetailScreenItemStartEndPadding,
                end = CategoryDetailScreenItemStartEndPadding,
                top = CategoryDetailScreenItemTopBottomPadding,
                bottom = CategoryDetailScreenItemTopBottomPadding
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    data.category,
                    style = CategoryDetailItemCategoryTextStyle.copy(color = Blue1)
                )
                Spacer(Modifier.height(CategoryDetailScreenSpacer6))

                Text(
                    "-${formatAmount(data.amount)}",
                    style = CategoryDetailItemAmountTextStyle.copy(color = Black)
                )
            }

            Text(
                data.memo,
                style = CategoryDetailItemMemoTextStyle.copy(color = Gray1)
            )
        }
    }
}

@Preview
@Composable
fun CategoryDetailScreenPreview() {
    CategoryDetail()
}