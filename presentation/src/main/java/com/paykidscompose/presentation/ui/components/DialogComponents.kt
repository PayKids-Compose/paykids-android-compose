package com.paykidscompose.presentation.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.paykidscompose.common.enums.AllowanceType
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.ui.components.util.PopupType
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
import com.paykidscompose.presentation.ui.theme.Blue1
import com.paykidscompose.presentation.ui.theme.Gray1
import com.paykidscompose.presentation.ui.theme.Gray5
import com.paykidscompose.presentation.ui.theme.Gray6
import com.paykidscompose.presentation.ui.theme.Gray7
import com.paykidscompose.presentation.ui.theme.PopupDialogButtonShape
import com.paykidscompose.presentation.ui.theme.PopupDialogButtonTextStyle
import com.paykidscompose.presentation.ui.theme.PopupDialogCardColumnSize
import com.paykidscompose.presentation.ui.theme.PopupDialogCardShape
import com.paykidscompose.presentation.ui.theme.PopupDialogCardStartEndPadding
import com.paykidscompose.presentation.ui.theme.PopupDialogCardTopBottomPadding
import com.paykidscompose.presentation.ui.theme.PopupDialogSpacer20
import com.paykidscompose.presentation.ui.theme.PopupDialogSpacer6
import com.paykidscompose.presentation.ui.theme.PopupDialogSpacer8
import com.paykidscompose.presentation.ui.theme.PopupDialogTitleInfoTextStyle
import com.paykidscompose.presentation.ui.theme.PopupDialogTitleTextStyle
import com.paykidscompose.presentation.ui.theme.Red
import com.paykidscompose.presentation.ui.theme.White
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map


@Composable
fun PopupDialog(
    title: String,
    description: String,
    popupType: PopupType,
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    Dialog(
        onDismissRequest = { onCancelClick() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        )
    ) {
        Card(
            shape = RoundedCornerShape(PopupDialogCardShape),
            colors = CardDefaults.cardColors(
                containerColor = White
            )
        ) {
            Column(
                modifier = Modifier
                    .width(PopupDialogCardColumnSize)
                    .padding(
                        start = PopupDialogCardStartEndPadding,
                        end = PopupDialogCardStartEndPadding,
                        top = PopupDialogCardTopBottomPadding,
                        bottom = PopupDialogCardTopBottomPadding
                    )
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = PopupDialogTitleTextStyle.copy(color = Black),
                    textAlign = TextAlign.Center
                )

                if (description != "") {
                    Spacer(modifier = Modifier.height(PopupDialogSpacer8))

                    Text(
                        text = description,
                        style = PopupDialogTitleInfoTextStyle.copy(color = Gray1)
                    )
                }

                Spacer(modifier = Modifier.height(PopupDialogSpacer20))

                when (popupType) {
                    PopupType.LOGOUT -> {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min)
                        ) {

                            Button(
                                onClick = onConfirmClick,
                                shape = RoundedCornerShape(PopupDialogButtonShape),
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Red, // 버튼 배경색상
                                    contentColor = White, // 버튼 텍스트 색상
                                )
                            ) {
                                Text(
                                    text = stringResource(R.string.dialog_signout_confirm),
                                    style = PopupDialogButtonTextStyle
                                )
                            }

                            Spacer(modifier = Modifier.width(PopupDialogSpacer6))

                            Button(
                                onClick = onCancelClick,
                                shape = RoundedCornerShape(PopupDialogButtonShape),
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Gray6, // 버튼 배경색상
                                    contentColor = Black, // 버튼 텍스트 색상
                                )
                            ) {
                                Text(
                                    text = stringResource(R.string.dialog_cancel),
                                    style = PopupDialogButtonTextStyle
                                )
                            }
                        }
                    }

                    PopupType.USER_DELETE -> {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min)
                        ) {
                            Button(
                                onClick = onCancelClick,
                                shape = RoundedCornerShape(PopupDialogButtonShape),
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Gray6, // 버튼 배경색상
                                    contentColor = Black, // 버튼 텍스트 색상
                                )
                            ) {
                                Text(
                                    text = stringResource(R.string.dialog_cancel),
                                    style = PopupDialogButtonTextStyle
                                )
                            }

                            Spacer(modifier = Modifier.width(PopupDialogSpacer6))

                            Button(
                                onClick = onConfirmClick,
                                shape = RoundedCornerShape(PopupDialogButtonShape),
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Red, // 버튼 배경색상
                                    contentColor = White, // 버튼 텍스트 색상
                                )
                            ) {
                                Text(
                                    text = stringResource(R.string.dialog_withdraw_confirm),
                                    style = PopupDialogButtonTextStyle
                                )
                            }
                        }
                    }

                    PopupType.INCORRECT_ANSWER_NOTE_ERROR -> {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min)
                        ) {
                            Button(
                                onClick = onConfirmClick,
                                shape = RoundedCornerShape(PopupDialogButtonShape),
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Blue1, // 버튼 배경색상
                                    contentColor = White, // 버튼 텍스트 색상
                                )
                            ) {
                                Text(
                                    text = stringResource(R.string.dialog_enter_quiz),
                                    style = PopupDialogButtonTextStyle
                                )
                            }

                            Spacer(modifier = Modifier.width(PopupDialogSpacer6))

                            Button(
                                onClick = onCancelClick,
                                shape = RoundedCornerShape(PopupDialogButtonShape),
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Gray6, // 버튼 배경색상
                                    contentColor = Black, // 버튼 텍스트 색상
                                )
                            ) {
                                Text(
                                    text = stringResource(R.string.dialog_exit),
                                    style = PopupDialogButtonTextStyle
                                )
                            }
                        }
                    }

                    PopupType.QUIZ_EXIT -> {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min)
                        ) {
                            Button(
                                onClick = onCancelClick,
                                shape = RoundedCornerShape(PopupDialogButtonShape),
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Blue1, // 버튼 배경색상
                                    contentColor = White, // 버튼 텍스트 색상
                                )
                            ) {
                                Text(
                                    text = stringResource(R.string.dialog_continue),
                                    style = PopupDialogButtonTextStyle
                                )
                            }

                            Spacer(modifier = Modifier.width(PopupDialogSpacer6))

                            Button(
                                onClick = onConfirmClick,
                                shape = RoundedCornerShape(PopupDialogButtonShape),
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Gray6, // 버튼 배경색상
                                    contentColor = Black, // 버튼 텍스트 색상
                                )
                            ) {
                                Text(
                                    text = stringResource(R.string.dialog_exit2),
                                    style = PopupDialogButtonTextStyle
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun AllowanceInputDialog(
    selected: AllowanceType = AllowanceType.EXPENSE,
    onSelect: (AllowanceType) -> Unit,
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    val categoryList = listOf("식비", "교통", "문화", "쇼핑", "기타")

    var showCategoryPicker by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(categoryList.firstOrNull()) }

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
                            .background(color = if (selected == AllowanceType.EXPENSE) White else Gray5)
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
                            .background(color = if (selected == AllowanceType.INCOME) White else Gray5)
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
                            DateInputItem("2024년") // 얘네는 이 컴포저블을 호출한 곳에서 LocalDate 객체를 보내줄거임
                            // 지금은 화면만 만든 것

                            DateInputItem("12월")

                            DateInputItem("11일")

                        }
                    }

                    Spacer(Modifier.height(AllowanceInputDialogSpacer12))

                    Text(
                        stringResource(R.string.dialog_text_add_amount),
                        style = AllowanceInputItemTitleTextStyle.copy(color = Black)
                    )

                    Spacer(Modifier.height(AllowanceInputDialogSpacer8))

                    OutlineInputField(
                        "", // 여기서도 호출한 곳에서 amount 를 보내줄거임
                        { },
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
                        "", // 호출한 곳에서 memo를 보내줄거임
                        { },
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
                            selectedCategory.toString()
                                ?: stringResource(R.string.dialog_hint_category),
                            style = AllowanceInputItemTextStyle.copy(color = Black)
                        )
                    }

                    Spacer(Modifier.height(AllowanceInputDialogSpacer40))

                    Button(
                        onClick = onConfirmClick,
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
                        categories = categoryList,
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
    date: String
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = {},
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
            Text(date, style = AllowanceInputDateAndConfirmButtonTextStyle.copy(color = Gray1))
        }

        Spacer(Modifier.height(AllowanceInputDialogSpacer6))

        IconButton(
            onClick = {},
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
    AllowanceInputDialog(AllowanceType.EXPENSE, {}, {}) { }
}

@Preview
@Composable
fun PopupDialogPreview() {
    PopupDialog("회원 탈퇴하시겠습니까?", "이것은 테스트 팝업입니다.", PopupType.LOGOUT, {}, {})
}

@Preview
@Composable
fun PopupDialogPreview2() {
    PopupDialog(
        stringResource(R.string.dialog_incorrect_nothing),
        "",
        PopupType.INCORRECT_ANSWER_NOTE_ERROR,
        {},
        {})
}

@Preview
@Composable
fun PopupDialogPreview3() {
    PopupDialog(stringResource(R.string.dialog_check_exit), "", PopupType.QUIZ_EXIT, {}, {})
}