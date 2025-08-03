package com.paykidscompose.presentation.screens.allowance.analysis

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paykidscompose.common.enums.AllowanceType
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.util.MonthFormatter
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.model.allowance.AllowanceChartCategoryUIModel
import com.paykidscompose.presentation.model.allowance.CategoryProgressBarUIModel
import com.paykidscompose.presentation.ui.components.ScreenLoading
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryHeadMonthTextStyle
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryScreenCardShape
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryScreenHeadIconSize
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryScreenTransactionStartEndPadding
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryScreenTransactionTopBottomPadding
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryTitleExpenseTextStyle
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogCardDateBorderWidth
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogCardDateRowHeight
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogExpenseIncomeBoxElevation
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogShape
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogToggleShadowColor
import com.paykidscompose.presentation.ui.theme.AllowanceInputToggleTextStyle
import com.paykidscompose.presentation.ui.theme.AnalysisScreenAddButtonVertical
import com.paykidscompose.presentation.ui.theme.AnalysisScreenBorderWidth1
import com.paykidscompose.presentation.ui.theme.AnalysisScreenDeleteButtonHorizontal
import com.paykidscompose.presentation.ui.theme.AnalysisScreenDeleteButtonVertical
import com.paykidscompose.presentation.ui.theme.AnalysisScreenElevation16
import com.paykidscompose.presentation.ui.theme.AnalysisScreenProgressBarHeight
import com.paykidscompose.presentation.ui.theme.AnalysisScreenProgressBarSize
import com.paykidscompose.presentation.ui.theme.AnalysisScreenProgressBarSpace
import com.paykidscompose.presentation.ui.theme.AnalysisScreenShape10
import com.paykidscompose.presentation.ui.theme.AnalysisScreenShape100
import com.paykidscompose.presentation.ui.theme.AnalysisScreenShape4
import com.paykidscompose.presentation.ui.theme.AnalysisScreenShape5
import com.paykidscompose.presentation.ui.theme.AnalysisScreenShape8
import com.paykidscompose.presentation.ui.theme.AnalysisScreenSpacer12
import com.paykidscompose.presentation.ui.theme.AnalysisScreenSpacer28
import com.paykidscompose.presentation.ui.theme.AnalysisScreenSpacer34
import com.paykidscompose.presentation.ui.theme.AnalysisScreenSpacer8
import com.paykidscompose.presentation.ui.theme.AnalysisScreenStartEndPadding
import com.paykidscompose.presentation.ui.theme.AnalysisScreenTopPadding
import com.paykidscompose.presentation.ui.theme.Black
import com.paykidscompose.presentation.ui.theme.Blue1
import com.paykidscompose.presentation.ui.theme.CustomCardShadow
import com.paykidscompose.presentation.ui.theme.ExpenseAnalysisDeleteButtonTextStyle
import com.paykidscompose.presentation.ui.theme.ExpenseAnalysisItemAddButtonTextStyle
import com.paykidscompose.presentation.ui.theme.ExpenseAnalysisItemAmountTextStyle
import com.paykidscompose.presentation.ui.theme.ExpenseAnalysisItemCategoryTextStyle
import com.paykidscompose.presentation.ui.theme.ExpenseAnalysisItemPercentTextStyle
import com.paykidscompose.presentation.ui.theme.ExpenseAnalysisProgressBarNameTextStyle
import com.paykidscompose.presentation.ui.theme.Gray1
import com.paykidscompose.presentation.ui.theme.Gray5
import com.paykidscompose.presentation.ui.theme.Gray6
import com.paykidscompose.presentation.ui.theme.Gray7
import com.paykidscompose.presentation.ui.theme.Gray9
import com.paykidscompose.presentation.ui.theme.MyPageCardShadowColor
import com.paykidscompose.presentation.ui.theme.White
import com.paykidscompose.presentation.ui.theme.White2
import com.paykidscompose.presentation.util.assignColorsToCategories
import com.paykidscompose.presentation.util.formatAmount
import java.time.LocalDate

@Composable
fun TransactionAnalysis(
    viewModel: TransactionAnalysisViewModel = viewModel(),
    onCategoryCard: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            when (it) {
                is PayKidsException.DialogException -> {

                }

                is PayKidsException.SnackBarException -> {

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

        else -> {
            TransactionAnalysisScreen(
                uiState.uiModel.transactionList,
                uiState.totalAmount,
                onCategoryCard,
                uiState.currentMonth,
                uiState.selectedType,
                { viewModel.onPrevMonth() },
                { viewModel.onNextMonth() },
                { viewModel.onAllowanceTypeSelected(it) }
            )
        }
    }
}

@Composable
fun TransactionAnalysisScreen(
    transactionList: List<AllowanceChartCategoryUIModel>,
    totalAmount: Int,
    onCategoryCard: () -> Unit,
    month: LocalDate,
    selected: AllowanceType,
    onPrevMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onSelect: (AllowanceType) -> Unit,
) {
    val progressBarUIModels = remember(transactionList) {
        assignColorsToCategories(transactionList)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Gray5)
            .statusBarsPadding()
            .padding(
                start = AnalysisScreenStartEndPadding,
                end = AnalysisScreenStartEndPadding,
                top = AnalysisScreenTopPadding
            )
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = {
                    onPrevMonth()
                },
                modifier = Modifier.size(AllowanceDiaryScreenHeadIconSize)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_left_round),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }

            Text(
                text = month.format(MonthFormatter), style = AllowanceDiaryHeadMonthTextStyle
            )

            IconButton(
                onClick = {
                    onNextMonth()
                },
                modifier = Modifier.size(AllowanceDiaryScreenHeadIconSize)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_right_round),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }

        Spacer(Modifier.height(AnalysisScreenSpacer28))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(AllowanceInputDialogCardDateRowHeight)
                .clip(RoundedCornerShape(AllowanceInputDialogShape))
                .background(color = Gray9)
                .border(
                    width = AllowanceInputDialogCardDateBorderWidth,
                    color = Gray6,
                    shape = RoundedCornerShape(AllowanceInputDialogShape)
                )
        ) {

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .then(
                        if (selected == AllowanceType.EXPENSE)
                            Modifier.shadow(
                                elevation = AllowanceInputDialogExpenseIncomeBoxElevation,
                                shape = RoundedCornerShape(AllowanceInputDialogShape),
                                ambientColor = AllowanceInputDialogToggleShadowColor,
                                spotColor = AllowanceInputDialogToggleShadowColor
                            )
                        else
                            Modifier
                    )
                    .clip(RoundedCornerShape(AllowanceInputDialogShape))
                    .background(color = if (selected == AllowanceType.EXPENSE) White else Gray9)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onSelect(AllowanceType.EXPENSE)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    stringResource(R.string.dialog_switch_consum),
                    style = AllowanceInputToggleTextStyle.copy(color = if (selected == AllowanceType.EXPENSE) Black else Gray7)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .then(
                        if (selected == AllowanceType.INCOME)
                            Modifier.shadow(
                                elevation = AllowanceInputDialogExpenseIncomeBoxElevation,
                                shape = RoundedCornerShape(AllowanceInputDialogShape),
                                ambientColor = AllowanceInputDialogToggleShadowColor,
                                spotColor = AllowanceInputDialogToggleShadowColor
                            )
                        else
                            Modifier
                    )
                    .clip(RoundedCornerShape(AllowanceInputDialogShape))
                    .background(color = if (selected == AllowanceType.INCOME) White else Gray9)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onSelect(AllowanceType.INCOME)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    stringResource(R.string.dialog_switch_income),
                    style = AllowanceInputToggleTextStyle.copy(color = if (selected == AllowanceType.INCOME) Black else Gray7)
                )
            }
        }

        Spacer(Modifier.height(AnalysisScreenSpacer34))

        Text(
            stringResource(R.string.text_month_total_consume, formatAmount(totalAmount)),
            style = AllowanceDiaryTitleExpenseTextStyle
                .copy(color = Black)
        )

        Spacer(Modifier.height(AnalysisScreenSpacer12))

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CategoryProgressBar(progressBarUIModels)
        }

        Spacer(Modifier.height(AnalysisScreenSpacer34))

        Button(
            onClick = {},
            shape = RoundedCornerShape(AnalysisScreenShape5),
            colors = ButtonDefaults.buttonColors(
                containerColor = Gray7,
                contentColor = White
            ),
            contentPadding = PaddingValues(
                horizontal = AnalysisScreenDeleteButtonHorizontal,
                vertical = AnalysisScreenDeleteButtonVertical
            ),
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(stringResource(R.string.text_delete), style = ExpenseAnalysisDeleteButtonTextStyle)
        }

        Spacer(Modifier.height(AnalysisScreenSpacer12))

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(transactionList) {
                AnalysisItem(
                    it.category, it.amount,
                    "${it.percent}%", onCategoryCard
                )
                Spacer(Modifier.height(AnalysisScreenSpacer8))
            }

            item {
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(AnalysisScreenShape10),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White2,
                        contentColor = Gray7
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            AnalysisScreenElevation16,
                            shape = RoundedCornerShape(AnalysisScreenShape10),
                            ambientColor = MyPageCardShadowColor,
                            spotColor = MyPageCardShadowColor
                        ),
                    contentPadding = PaddingValues(vertical = AnalysisScreenAddButtonVertical)
                ) {
                    Text(
                        stringResource(R.string.text_add_category),
                        style = ExpenseAnalysisItemAddButtonTextStyle
                    )
                }
            }

        }
    }
}

@Composable
fun AnalysisItem(
    category: String,
    amount: Int,
    percent: String,
    onCategoryCard: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = {
                    onCategoryCard()
                }
            )
            .shadow(
                elevation = CustomCardShadow,
                shape = RoundedCornerShape(AllowanceDiaryScreenCardShape),
                ambientColor = MyPageCardShadowColor,
                spotColor = MyPageCardShadowColor
            )
            .background(
                White, shape = RoundedCornerShape(AllowanceDiaryScreenCardShape)
            )
            .padding(
                start = AllowanceDiaryScreenTransactionStartEndPadding,
                end = AllowanceDiaryScreenTransactionStartEndPadding,
                top = AllowanceDiaryScreenTransactionTopBottomPadding,
                bottom = AllowanceDiaryScreenTransactionTopBottomPadding
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                category,
                style = ExpenseAnalysisItemCategoryTextStyle.copy(color = Blue1)
            )

            Text(
                formatAmount(amount),
                style = ExpenseAnalysisItemAmountTextStyle.copy(color = Black)
            )

            Text(
                percent,
                style = ExpenseAnalysisItemPercentTextStyle.copy(color = Gray1)
            )
        }
    }
}


@Composable
private fun CategoryProgressBar(progressBarUIModels: List<CategoryProgressBarUIModel>) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(AnalysisScreenProgressBarHeight)
                .clip(RoundedCornerShape(AnalysisScreenShape100))
                .border(AnalysisScreenBorderWidth1, Gray6, RoundedCornerShape(AnalysisScreenShape100))
        ) {
            Row(Modifier.fillMaxSize()) {
                progressBarUIModels.forEach { item ->
                    Box(
                        modifier = Modifier
                            .weight(item.percent)
                            .fillMaxHeight()
                            .background(item.color)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(AnalysisScreenShape8))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AnalysisScreenProgressBarSpace),
            verticalAlignment = Alignment.CenterVertically
        ) {
            progressBarUIModels.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(AnalysisScreenProgressBarSize)
                            .background(item.color, shape = CircleShape)
                    )
                    Spacer(Modifier.width(AnalysisScreenShape4))
                    Text(
                        text = item.name,
                        style = ExpenseAnalysisProgressBarNameTextStyle
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun AnalysisScreenPreview() {
    TransactionAnalysis()
}