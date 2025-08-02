package com.paykidscompose.presentation.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.paykidscompose.common.enums.AllowanceType
import com.paykidscompose.common.util.today
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.model.allowance.AllowanceChartUIModel
import com.paykidscompose.presentation.model.allowance.CategoryUIModel
import com.paykidscompose.presentation.ui.theme.AllowanceInputDateAndConfirmButtonTextStyle
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogAmountFieldTopBottomPadding
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogCardDateBorderWidth
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogCardDateRowHeight
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogCardShape
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogCardStartEndPadding
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogCardTopBottomPadding
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogCategoryCardShape
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogCategoryStartPadding
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogCategoryTopBottomPadding
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogConfirmButtonStartEndPadding
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogConfirmButtonTopBottomPadding
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogDateBoxStartEndPadding
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogDateBoxTopBottomPadding
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogDateItemStartEndPadding
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogDateItemTopBottomPadding
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogExpenseIncomeBoxElevation
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogIconHeight
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogIconWidth
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogInputFieldStartEndPadding
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogMemoFieldBottomPadding
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogMemoFieldTopPadding
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogShape
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogSpacer12
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogSpacer20
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogSpacer26
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogSpacer40
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogSpacer6
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogSpacer8
import com.paykidscompose.presentation.ui.theme.AllowanceInputDialogToggleShadowColor
import com.paykidscompose.presentation.ui.theme.AllowanceInputItemTextStyle
import com.paykidscompose.presentation.ui.theme.AllowanceInputItemTitleTextStyle
import com.paykidscompose.presentation.ui.theme.AllowanceInputTitleTextStyle
import com.paykidscompose.presentation.ui.theme.AllowanceInputToggleTextStyle
import com.paykidscompose.presentation.ui.theme.Black
import com.paykidscompose.presentation.ui.theme.Gray1
import com.paykidscompose.presentation.ui.theme.Gray5
import com.paykidscompose.presentation.ui.theme.Gray6
import com.paykidscompose.presentation.ui.theme.Gray7
import com.paykidscompose.presentation.ui.theme.PopupDialogCardColumnSize
import com.paykidscompose.presentation.ui.theme.White
import com.paykidscompose.presentation.util.formatAmount
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.time.LocalDate


@Composable
fun AllowanceInputDialog(
    chartUIModel: AllowanceChartUIModel,
    expenseCategories: List<CategoryUIModel> = emptyList(),
    incomeCategories: List<CategoryUIModel> = emptyList(),
    isReplace: Boolean = false,
    onCancelClick: () -> Unit,
    onConfirmClick: (AllowanceChartUIModel) -> Unit
) {
    var year by remember { mutableIntStateOf(chartUIModel.date.year) }
    var month by remember { mutableIntStateOf(chartUIModel.date.monthValue) }
    var day by remember { mutableIntStateOf(chartUIModel.date.dayOfMonth) }
    var rawAmount by remember { mutableStateOf(chartUIModel.amount.toString()) }
    val inputAmount =
        if (rawAmount.isNotEmpty() && rawAmount.toInt() != 0) formatAmount(rawAmount.toInt()) else ""
    var inputMemo by remember { mutableStateOf(chartUIModel.memo) }
    var selectType by remember { mutableStateOf(chartUIModel.type) }
    var showCategoryPicker by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(chartUIModel.category) }

    val onSelectType = { type: AllowanceType ->
        if(!isReplace) {
            selectType = type
        }
    }

    val onPrevYear = {
        year -= 1
    }

    val onNextYear = {
        year += 1
    }

    val onPrevMonth = {
        month = if (month == 1) 12.also { year -= 1 } else month - 1
    }

    val onNextMonth = {
        month = if (month == 12) 1.also { year += 1 } else month + 1
    }

    val onPrevDay = {
        val current = LocalDate.of(year, month, day)
        val prev = current.minusDays(1)
        year = prev.year
        month = prev.monthValue
        day = prev.dayOfMonth
    }

    val onNextDay = {
        val current = LocalDate.of(year, month, day)
        val prev = current.plusDays(1)
        year = prev.year
        month = prev.monthValue
        day = prev.dayOfMonth
    }

    Dialog(
        onDismissRequest = { onCancelClick() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        )
    ) {
        Card(
            shape = RoundedCornerShape(AllowanceInputDialogCardShape),
            colors = CardDefaults.cardColors(
                containerColor = White
            )
        ) {
            Column(
                modifier = Modifier
                    .width(PopupDialogCardColumnSize)
                    .padding(
                        start = AllowanceInputDialogCardStartEndPadding,
                        end = AllowanceInputDialogCardStartEndPadding,
                        top = AllowanceInputDialogCardTopBottomPadding,
                        bottom = AllowanceInputDialogCardTopBottomPadding
                    )
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.dialog_add_title),
                    style = AllowanceInputTitleTextStyle.copy(color = Black)
                )

                Spacer(Modifier.height(AllowanceInputDialogSpacer26))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AllowanceInputDialogCardDateRowHeight)
                        .clip(RoundedCornerShape(AllowanceInputDialogShape))
                        .background(color = Gray5)
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
                                if (selectType == AllowanceType.EXPENSE)
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
                            .background(color = if (selectType == AllowanceType.EXPENSE) White else Gray5)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                onSelectType(AllowanceType.EXPENSE)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            stringResource(R.string.dialog_switch_consum),
                            style = AllowanceInputToggleTextStyle.copy(color = if (selectType == AllowanceType.EXPENSE) Black else Gray7)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .then(
                                if (selectType == AllowanceType.INCOME)
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
                            .background(color = if (selectType == AllowanceType.INCOME) White else Gray5)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                onSelectType(AllowanceType.INCOME)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            stringResource(R.string.dialog_switch_income),
                            style = AllowanceInputToggleTextStyle.copy(color = if (selectType == AllowanceType.INCOME) Black else Gray7)
                        )
                    }
                }

                Spacer(Modifier.height(AllowanceInputDialogSpacer20))

                Column(
                    modifier = Modifier.fillMaxWidth(),

                    ) {
                    Text(
                        stringResource(R.string.dialog_text_date),
                        style = AllowanceInputItemTitleTextStyle.copy(color = Black)
                    )

                    Spacer(Modifier.height(AllowanceInputDialogSpacer8))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(AllowanceInputDialogCardShape))
                            .background(color = Gray5)
                            .border(
                                width = AllowanceInputDialogCardDateBorderWidth,
                                color = Gray6,
                                shape = RoundedCornerShape(AllowanceInputDialogCardShape)
                            )
                            .padding(
                                start = AllowanceInputDialogDateBoxStartEndPadding,
                                end = AllowanceInputDialogDateBoxStartEndPadding,
                                top = AllowanceInputDialogDateBoxTopBottomPadding,
                                bottom = AllowanceInputDialogDateBoxTopBottomPadding
                            )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            DateInputItem(year, "년", onPrevYear, onNextYear)

                            DateInputItem(month, "월", onPrevMonth, onNextMonth)

                            DateInputItem(day, "일", onPrevDay, onNextDay)

                        }
                    }

                    Spacer(Modifier.height(AllowanceInputDialogSpacer12))

                    Text(
                        stringResource(R.string.dialog_text_add_amount),
                        style = AllowanceInputItemTitleTextStyle.copy(color = Black)
                    )

                    Spacer(Modifier.height(AllowanceInputDialogSpacer8))

                    OutlineInputField(
                        inputAmount,
                        { input ->
                            rawAmount = input.filter {
                                it.isDigit()
                            }
                        },
                        startPadding = AllowanceInputDialogInputFieldStartEndPadding,
                        backgroundColor = Gray5,
                        modifier = Modifier.padding(
                            end = AllowanceInputDialogInputFieldStartEndPadding,
                            top = AllowanceInputDialogAmountFieldTopBottomPadding,
                            bottom = AllowanceInputDialogAmountFieldTopBottomPadding
                        ),
                        hint = stringResource(R.string.dialog_hint_add_amount),
                        textColor = Gray1,
                        style = AllowanceInputItemTextStyle
                    )

                    Spacer(Modifier.height(AllowanceInputDialogSpacer12))

                    Text("메모", style = AllowanceInputItemTitleTextStyle.copy(color = Black))

                    Spacer(Modifier.height(AllowanceInputDialogSpacer8))

                    OutlineInputField(
                        inputMemo, // 호출한 곳에서 memo를 보내줄거임
                        { inputMemo = it },
                        startPadding = AllowanceInputDialogInputFieldStartEndPadding,
                        backgroundColor = Gray5,
                        modifier = Modifier.padding(
                            end = AllowanceInputDialogInputFieldStartEndPadding,
                            top = AllowanceInputDialogMemoFieldTopPadding,
                            bottom = AllowanceInputDialogMemoFieldBottomPadding
                        ),
                        hint = stringResource(R.string.dialog_hint_memo),
                        textColor = Gray1,
                        style = AllowanceInputItemTextStyle,
                        singleLine = false
                    )

                    Spacer(Modifier.height(AllowanceInputDialogSpacer12))

                    Text(
                        stringResource(R.string.dialog_text_category),
                        style = AllowanceInputItemTitleTextStyle.copy(color = Black)
                    )

                    Spacer(Modifier.height(AllowanceInputDialogSpacer8))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(AllowanceInputDialogCategoryCardShape))
                            .background(color = Gray5)
                            .border(
                                width = AllowanceInputDialogCardDateBorderWidth,
                                color = Gray6,
                                shape = RoundedCornerShape(AllowanceInputDialogCategoryCardShape)
                            )
                            .padding(
                                start = AllowanceInputDialogCategoryStartPadding,
                                top = AllowanceInputDialogCategoryTopBottomPadding,
                                bottom = AllowanceInputDialogCategoryTopBottomPadding
                            )
                            .clickable(onClick = {
                                showCategoryPicker = true
                            }),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            selectedCategory,
                            style = AllowanceInputItemTextStyle.copy(color = Black)
                        )
                    }

                    Spacer(Modifier.height(AllowanceInputDialogSpacer40))

                    Button(
                        onClick = {
                            onConfirmClick(
                                chartUIModel.copy(
                                    date = LocalDate.of(year, month, day),
                                    type = selectType,
                                    category = selectedCategory,
                                    amountFormatted = inputAmount,
                                    amount = rawAmount.toInt(),
                                    memo = inputMemo
                                )
                            )
                        },
                        modifier = Modifier
                            .padding(
                                start = AllowanceInputDialogConfirmButtonStartEndPadding,
                                end = AllowanceInputDialogConfirmButtonStartEndPadding
                            )
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(AllowanceInputDialogCardShape),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Gray6,
                            contentColor = Black
                        ),
                        contentPadding = PaddingValues(vertical = AllowanceInputDialogConfirmButtonTopBottomPadding)
                    ) {
                        Text(
                            stringResource(R.string.dialog_text_submit),
                            style = AllowanceInputDateAndConfirmButtonTextStyle.copy(color = Black)
                        )
                    }
                }
            }
        }

        if (showCategoryPicker) {
            Dialog(
                onDismissRequest = { showCategoryPicker = false }, // 다이얼로그 외부 클릭 시 닫기
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                )
            ) {
                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    modifier = Modifier.wrapContentSize()
                ) {

                    CategoryWheelPicker(
                        categories = if (selectType == AllowanceType.EXPENSE) expenseCategories.map { it.title } else incomeCategories.map { it.title },
                        initialSelectedCategory = selectedCategory
                    ) { selected ->
                        selectedCategory = selected
                        showCategoryPicker = false
                    }

                }
            }
        }
    }
}

// 완성된 함수가 아닙니다. 계속 수정 해야합니다.
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryWheelPicker(
    categories: List<String>,
    initialSelectedCategory: String?,
    onCategorySelected: (String) -> Unit
) {
    val listState = rememberLazyListState()

    var selectedCategory by remember {
        mutableStateOf(
            initialSelectedCategory ?: categories.firstOrNull()
        )
    }

    LaunchedEffect(listState, categories) {
        initialSelectedCategory?.let { initial ->
            val initialIndex = categories.indexOf(initial)
            if (initialIndex != -1) {
                val offset =
                    (listState.layoutInfo.visibleItemsInfo.firstOrNull()?.size?.div(2) ?: 0)
                listState.scrollToItem(initialIndex, -offset)
            }
        }

        snapshotFlow { listState.firstVisibleItemIndex }
            .map { firstIndex ->
                val centerItemOffset = (listState.layoutInfo.visibleItemsInfo.size / 2)
                categories.getOrNull(firstIndex + centerItemOffset)
            }
            .distinctUntilChanged()
            .collect { category ->
                category?.let {
                    selectedCategory = it
                    onCategorySelected(it)
                }
            }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .align(Alignment.Center)
                .background(Color.LightGray.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
        )

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,

            contentPadding = PaddingValues(vertical = 75.dp)
        ) {
            items(categories.size) { index ->
                val category = categories[index]
                val isSelected = category == selectedCategory

                val alpha = if (isSelected) 1f else 0.4f
                val fontSize = if (isSelected) 24.sp else 18.sp

                Text(
                    text = category,
                    fontSize = fontSize,
                    color = Black,
                    modifier = Modifier
                        .height(50.dp)
                        .wrapContentHeight(align = Alignment.CenterVertically)
                        .alpha(alpha)
                )
            }
        }
    }
}


@Composable
private fun DateInputItem( // 여기도 문자열 말고 LocalDate로 수정 예정
    date: Int,
    unit: String,
    onPrev: () -> Unit,
    onNext: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = {
                onNext()
            },
            modifier = Modifier.size(
                width = AllowanceInputDialogIconWidth,
                height = AllowanceInputDialogIconHeight
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_up),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }

        Spacer(Modifier.height(AllowanceInputDialogSpacer6))

        Box(
            modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(AllowanceInputDialogShape))
                .background(color = White)
                .border(
                    width = AllowanceInputDialogCardDateBorderWidth,
                    color = Gray6,
                    RoundedCornerShape(AllowanceInputDialogShape)
                )
                .padding(
                    start = AllowanceInputDialogDateItemStartEndPadding,
                    end = AllowanceInputDialogDateItemStartEndPadding,
                    top = AllowanceInputDialogDateItemTopBottomPadding,
                    bottom = AllowanceInputDialogDateItemTopBottomPadding
                )
        ) {
            Text(
                "${date.toString().padStart(2, '0')}$unit",
                style = AllowanceInputDateAndConfirmButtonTextStyle.copy(color = Gray1)
            )
        }

        Spacer(Modifier.height(AllowanceInputDialogSpacer6))

        IconButton(
            onClick = {
                onPrev()
            },
            modifier = Modifier.size(
                width = AllowanceInputDialogIconWidth,
                height = AllowanceInputDialogIconHeight
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_down),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
    }
}

@Preview
@Composable
fun AllowancePopup() {
    AllowanceInputDialog(
        chartUIModel = AllowanceChartUIModel(
            id = 0, // id는 서버에서 자체적으로 처리함.
            date = today,
            type = AllowanceType.EXPENSE,
            category = "기타",
            amountFormatted = "",
            amount = 0,
            memo = ""
        ),
        onCancelClick = { }
    ) { }
}
