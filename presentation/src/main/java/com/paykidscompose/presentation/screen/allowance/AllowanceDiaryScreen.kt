package com.paykidscompose.presentation.screen.allowance

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paykidscompose.common.enums.AllowanceType
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.util.MonthFormatter
import com.paykidscompose.common.util.today
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.base.UIEvent
import com.paykidscompose.presentation.model.allowance.AllowanceChartUIModel
import com.paykidscompose.presentation.ui.components.AllowanceInputDialog
import com.paykidscompose.presentation.ui.components.ScreenLoading
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryCalendarDayTextStyle
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryCalendarIncomeConsumeTextStyle
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryCalendarWeekTextStyle
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryDetailConsumeMonthDayTextStyle
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryDetailConsumeTitleTextStyle
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryHeadMonthTextStyle
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryMostConsumeTextStyle
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryMostConsumeTitleTextStyle
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryScreenCalendarBottomPadding
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryScreenCalendarStartEndTopPadding
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryScreenCardShape
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryScreenDayClickPadding
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryScreenDayClickSize
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryScreenExpenseCardEndPadding
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryScreenExpenseCardHeight
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryScreenExpenseCardStartPadding
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryScreenHeadAddIconSize
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryScreenHeadIconSize
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryScreenSpacer10
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryScreenSpacer16
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryScreenSpacer28
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryScreenSpacer34
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryScreenSpacer4
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryScreenSpacer6
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryScreenSpacer8
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryScreenStartEndPadding
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryScreenTopBottomPadding
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryScreenTransactionStartEndPadding
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryScreenTransactionTopBottomPadding
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryTitleExpenseTextStyle
import com.paykidscompose.presentation.ui.theme.Black
import com.paykidscompose.presentation.ui.theme.Blue1
import com.paykidscompose.presentation.ui.theme.Blue2
import com.paykidscompose.presentation.ui.theme.CustomCardShadow
import com.paykidscompose.presentation.ui.theme.Gray1
import com.paykidscompose.presentation.ui.theme.Gray3
import com.paykidscompose.presentation.ui.theme.Gray5
import com.paykidscompose.presentation.ui.theme.Gray8
import com.paykidscompose.presentation.ui.theme.MyPageCardShadowColor
import com.paykidscompose.presentation.ui.theme.Red
import com.paykidscompose.presentation.ui.theme.White
import com.paykidscompose.presentation.util.formatAmount
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.ceil

@Composable
fun AllowanceDiary(
    viewModel: AllowanceDiaryViewModel = viewModel(),
    onCategoryExpense: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    if (uiState.showAddDialog) {
        AllowanceInputDialog(
            chartUIModel = AllowanceChartUIModel(
                id = 0, // id는 서버에서 자체적으로 처리함.
                date = today,
                type = AllowanceType.EXPENSE,
                category = uiState.uiModel.expenseCategories.first().title,
                amountFormatted = "",
                amount = 0,
                memo = ""
            ),
            expenseCategories = uiState.uiModel.expenseCategories,
            incomeCategories = uiState.uiModel.incomeCategories,
            onCancelClick = { viewModel.onDismissAddDialog() }
        ) { viewModel.onConfirmAddDialog(it) }
    }

    if (uiState.showReplaceDialog && uiState.uiModel.selectedTransaction != null) {
        AllowanceInputDialog(
            chartUIModel = uiState.uiModel.selectedTransaction!!,
            expenseCategories = uiState.uiModel.expenseCategories,
            incomeCategories = uiState.uiModel.incomeCategories,
            isTypeLock = true,
            onCancelClick = { viewModel.onDismissReplaceDialog() }
        ) { viewModel.onConfirmReplaceDialog(it) }
    }

    LaunchedEffect(Unit) {
        viewModel.load()
        viewModel.getExpenseCategoryList()
        viewModel.getIncomeCategoryList()
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
            AllowanceDiaryScreen(
                uiState = uiState,
                onPrevMonth = { viewModel.onPrevMonth() },
                onNextMonth = { viewModel.onNextMonth() },
                onDateSelected = { viewModel.onSelectedDate(it) },
                onShowAddDialog = { viewModel.onAddDialog() },
                onShowReplaceDialog = { viewModel.onReplaceDialog(it) },
                onCategoryExpense = onCategoryExpense
            )
        }
    }
}

@Composable
fun AllowanceDiaryScreen(
    uiState: AllowanceDiaryUIState,
    onPrevMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    onShowAddDialog: () -> Unit,
    onShowReplaceDialog: (AllowanceChartUIModel) -> Unit,
    onCategoryExpense: () -> Unit = {},
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray5)
            .statusBarsPadding()
            .padding(
                start = AllowanceDiaryScreenStartEndPadding,
                end = AllowanceDiaryScreenStartEndPadding,
                top = AllowanceDiaryScreenTopBottomPadding
            ),
    ) {
        item {
            // < 12월 > + 버튼
            HeaderSection(
                month = uiState.currentMonth,
                onPrev = { onPrevMonth() },
                onNext = { onNextMonth() },
                onAddClick = {
                    onShowAddDialog()
                }
            )

            Spacer(Modifier.height(AllowanceDiaryScreenSpacer28))

            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    stringResource(
                        R.string.text_month_total_consume,
                        uiState.uiModel.headerTotalExpense
                    ),
                    style = AllowanceDiaryTitleExpenseTextStyle
                        .copy(color = Black)
                )

                Spacer(Modifier.height(AllowanceDiaryScreenSpacer16))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AllowanceDiaryScreenExpenseCardHeight)
                        .clickable(
                            onClick = {
                                onCategoryExpense()
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
                            start = AllowanceDiaryScreenExpenseCardStartPadding,
                            end = AllowanceDiaryScreenExpenseCardEndPadding
                        ),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (uiState.uiModel.mostCategoryExpense != null) {
                        Text(
                            buildAnnotatedString {
                                withStyle(SpanStyle(color = Black)) {
                                    append(uiState.currentMonth.format(DateTimeFormatter.ofPattern("M월 달 ")))
                                }
                                withStyle(SpanStyle(color = Blue1)) {
                                    append(uiState.uiModel.mostCategoryExpense.category)
                                }
                                withStyle(SpanStyle(color = Black)) {
                                    append(stringResource(R.string.text_month_most_consume2))
                                }
                            },
                            style = AllowanceDiaryMostConsumeTextStyle
                        )

                        Row(
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Text(
                                text = formatAmount(uiState.uiModel.mostCategoryExpense.amount),
                                modifier = Modifier.weight(1f, fill = false),
                                maxLines = 1,
                                textAlign = TextAlign.End,
                                style = AllowanceDiaryMostConsumeTitleTextStyle.copy(color = Black)
                            )

                            Spacer(modifier = Modifier.width(AllowanceDiaryScreenSpacer8))

                            Text(
                                stringResource(R.string.text_consuming),
                                modifier = Modifier.wrapContentWidth(),
                                style = AllowanceDiaryMostConsumeTextStyle
                            )
                        }
                    } else {
                        Text(
                            stringResource(R.string.text_month_no_consume),
                            style = AllowanceDiaryMostConsumeTextStyle.copy(color = Blue1)
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(AllowanceDiaryScreenSpacer16))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = CustomCardShadow,
                        ambientColor = MyPageCardShadowColor,
                        spotColor = MyPageCardShadowColor
                    )
                    .background(
                        White
                    )
                    .padding(
                        start = AllowanceDiaryScreenCalendarStartEndTopPadding,
                        end = AllowanceDiaryScreenCalendarStartEndTopPadding,
                        top = AllowanceDiaryScreenCalendarStartEndTopPadding,
                        bottom = AllowanceDiaryScreenCalendarBottomPadding
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CalendarGrid(
                        dailySummary = uiState.uiModel.monthlyDailyAmounts,
                        month = uiState.currentMonth,
                        selectedDate = uiState.selectedDate,
                        onDateSelected = onDateSelected
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(AllowanceDiaryScreenSpacer34))
            Text(
                stringResource(R.string.text_detail_consume),
                style = AllowanceDiaryDetailConsumeTitleTextStyle.copy(color = Black)
            )

            Spacer(Modifier.height(AllowanceDiaryScreenSpacer10))

            Text(
                text = uiState.selectedDate.format(DateTimeFormatter.ofPattern("M월 d일")),
                style = AllowanceDiaryDetailConsumeMonthDayTextStyle.copy(color = Gray8)
            )

            Spacer(Modifier.height(AllowanceDiaryScreenSpacer8))
        }

        items(uiState.uiModel.selectedDayTransactions) { item ->
            TransactionItem(item, onShowReplaceDialog)
            Spacer(Modifier.height(AllowanceDiaryScreenSpacer8))
        }
    }
}

@Composable
fun HeaderSection(
    month: LocalDate,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    onAddClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = onPrev,
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
                onClick = onNext,
                modifier = Modifier.size(AllowanceDiaryScreenHeadIconSize)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_right_round),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }

        IconButton(
            onClick = onAddClick,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(AllowanceDiaryScreenHeadAddIconSize)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_plus),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
    }

}

@Composable
fun CalendarGrid(
    dailySummary: Map<LocalDate, Pair<Int, Int>>,
    month: LocalDate,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val daysOfWeek = listOf(
        stringResource(R.string.text_week_sunday),
        stringResource(R.string.text_week_monday),
        stringResource(R.string.text_week_tuesday),
        stringResource(R.string.text_week_wednesday),
        stringResource(R.string.text_week_thursday),
        stringResource(R.string.text_week_friday),
        stringResource(R.string.text_week_saturday)
    )
    val firstDay = month.withDayOfMonth(1)
    val daysInMonth = month.lengthOfMonth()
    val startOffset =
        firstDay.dayOfWeek.value % 7 // 일요일 = 0, 달력이 어느 요일 부터 시작해야하는지 계산 2025-06-01 월요일이면 1 , 시작위치는 0~6

    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            daysOfWeek.forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier
                        .weight(1f),
                    textAlign = TextAlign.Center,
                    style = AllowanceDiaryCalendarWeekTextStyle.copy(color = Gray1)
                )
            }
        }

        Spacer(Modifier.height(AllowanceDiaryScreenSpacer4))

        val rows = ceil((startOffset + daysInMonth) / 7.0).toInt() // 일주일 단위로 배치 행 계산
        repeat(rows) { row ->
            Row(modifier = Modifier.fillMaxWidth()) {
                for (col in 0 until 7) { // 요일이 7개라서 열 = 7
                    val index = row * 7 + col
                    val day = index - startOffset + 1
                    if (index < startOffset || day > daysInMonth) {
                        // 빈칸처리 첫날이 수요일이라면 앞에 2칸은 빈칸이어야함.
                        // 말일 이후 칸은 더 이상 날짜가 없음
                        Spacer(modifier = Modifier.weight(1f))
                    } else {
                        val date = month.withDayOfMonth(day)
                        val isSelected = date == selectedDate
                        val isToday = date == today
                        val (expense, income) = dailySummary[date] ?: Pair(0, 0)

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(AllowanceDiaryScreenDayClickPadding)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }) {
                                    onDateSelected(date)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(AllowanceDiaryScreenDayClickSize) // 원 크기
                                        .clip(CircleShape)
                                        .background(
                                            when {
                                                isSelected -> Blue2
                                                isToday -> Gray3
                                                else -> Color.Transparent
                                            }
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = day.toString(),
                                        style = AllowanceDiaryCalendarDayTextStyle.copy(
                                            color = if (isSelected) White else Black
                                        )
                                    )
                                }

                                if (income > 0) {
                                    Text(
                                        "+${formatAmount(income)}",
                                        style = AllowanceDiaryCalendarIncomeConsumeTextStyle.copy(
                                            color = Blue1
                                        ),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                                if (expense > 0) {
                                    Text(
                                        "-${formatAmount(expense)}",
                                        style = AllowanceDiaryCalendarIncomeConsumeTextStyle.copy(
                                            color = Red
                                        ),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TransactionItem(
    item: AllowanceChartUIModel,
    onItemSelected: (AllowanceChartUIModel) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = {
                    onItemSelected(item)
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
        Column {
            Text(
                item.category,
                style = AllowanceDiaryMostConsumeTextStyle.copy(color = Blue1)
            )
            Spacer(Modifier.height(AllowanceDiaryScreenSpacer6))
            Text(
                item.amountFormatted,
                style = AllowanceDiaryMostConsumeTitleTextStyle.copy(color = Black)
            )
        }
        Text(
            item.memo,
            style = AllowanceDiaryMostConsumeTextStyle.copy(color = Gray1),
            modifier = Modifier.align(Alignment.CenterEnd),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
fun DiaryScreenPreview() {
    AllowanceDiary()
}