package com.paykidscompose.presentation.screens.allowance.analysis

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.paykidscompose.common.enums.AllowanceType
import com.paykidscompose.common.util.MonthFormatter
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.dummy.AllowanceChartDTO
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
import com.paykidscompose.presentation.util.formatAmount
import java.time.LocalDate

@Composable
fun ExpenseAnalysis(
    onCategoryCard: () -> Unit = {}
) {
    var currentMonth by remember {
        mutableStateOf(
            LocalDate.now()
        )
    }
    var selectedAllowanceType by remember { mutableStateOf(AllowanceType.EXPENSE) }

    val onSelectAllowanceType = { type: AllowanceType ->
        selectedAllowanceType = type
    }

    val onMonth = { month: LocalDate ->
        currentMonth = month
    }

    var showInputDialog by remember { mutableStateOf(false) }

    val onShowDialog = { value: Boolean ->
        showInputDialog = value
    }

    if (showInputDialog) {
//        AllowanceInputDialog(
//            onSelect = {},
//            onCancelClick = { showInputDialog = false }
//        ) { showInputDialog = false }
    }


    ExpenseAnalysisScreen(
        onCategoryCard,
        currentMonth,
        selectedAllowanceType,
        onMonth,
        onSelectAllowanceType
    )
}

@Composable
fun ExpenseAnalysisScreen(
    onCategoryCard: () -> Unit,
    month: LocalDate,
    selected: AllowanceType,
    onMonth: (LocalDate) -> Unit,
    onSelect: (AllowanceType) -> Unit,
) {
    val data = calculateCategoryStats(
        listOf(
            AllowanceChartDTO(1, "2025-06-01", AllowanceType.EXPENSE, "편의점", 3000, "음료"),
            AllowanceChartDTO(2, "2025-06-02", AllowanceType.EXPENSE, "식비", 12000, "점심"),
            AllowanceChartDTO(3, "2025-06-03", AllowanceType.EXPENSE, "편의점", 2000, "간식"),
            AllowanceChartDTO(4, "2025-06-04", AllowanceType.EXPENSE, "식비", 10000, "저녁"),
            AllowanceChartDTO(5, "2025-06-05", AllowanceType.EXPENSE, "기타", 10000, "저녁")
        )
    )


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
                    onMonth(month.minusMonths(1))
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
                    onMonth(month.plusMonths(1))
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
            stringResource(R.string.text_month_total_consume, formatAmount(150000)),
            style = AllowanceDiaryTitleExpenseTextStyle
                .copy(color = Black)
        )

        Spacer(Modifier.height(AnalysisScreenSpacer12))

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CategoryProgressBar(data)
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
            items(data) {
                AnalysisItem(
                    it.name, it.amount,
                    "${(it.percent * 100).toInt()}%", onCategoryCard
                )
                Spacer(Modifier.height(AnalysisScreenSpacer8))
            }

            item {
                AnalysisItem("기타", 0, "0%", onCategoryCard)
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
                    // CategoryDetailScreen으로 이동
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
private fun CategoryProgressBar(stats: List<CategoryStat>) {
    Column {
        // ProgressBar 스타일 박스
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(AnalysisScreenProgressBarHeight)
                .clip(RoundedCornerShape(AnalysisScreenShape100))
                .border(AnalysisScreenBorderWidth1, Gray6, RoundedCornerShape(AnalysisScreenShape100))
        ) {
            Row(Modifier.fillMaxSize()) {
                stats.forEach { stat ->
                    Box(
                        modifier = Modifier
                            .weight(stat.percent)
                            .fillMaxHeight()
                            .background(stat.color)
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
            stats.forEach { stat ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(AnalysisScreenProgressBarSize)
                            .background(stat.color, shape = CircleShape)
                    )
                    Spacer(Modifier.width(AnalysisScreenShape4))
                    Text(
                        text = stat.name,
                        style = ExpenseAnalysisProgressBarNameTextStyle
                    )
                }
            }
        }
    }
}

private data class CategoryStat(
    val name: String,
    val amount: Int,
    val percent: Float,
    val color: Color
)

private val categoryColors = mapOf(
    "편의점" to Color(0xFF1976D2),
    "식비" to Color(0xFF4FC3F7),
    "교통비" to Color(0xFF64B5F6),
    "문화생활" to Color(0xFFBA68C8),
    "기타" to Color.Gray
)

private fun calculateCategoryStats(data: List<AllowanceChartDTO>): List<CategoryStat> {
    val expenses = data.filter { it.allowanceType == AllowanceType.EXPENSE }

    val total = expenses.sumOf { it.amount }
    if (total == 0) return emptyList()

    return expenses
        .groupBy { it.category }
        .map { (category, items) ->
            val sum = items.sumOf { it.amount }
            CategoryStat(
                name = category,
                amount = sum,
                percent = sum.toFloat() / total,
                color = categoryColors[category] ?: categoryColors["기타"]!!
            )
        }
}

@Preview
@Composable
fun AnalysisScreenPreview() {
    ExpenseAnalysis()
}