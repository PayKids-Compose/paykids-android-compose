package com.paykidscompose.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.model.AllowanceType
import com.paykidscompose.presentation.ui.components.util.PopupType
import com.paykidscompose.presentation.ui.theme.AllowanceInputTitleTextStyle
import com.paykidscompose.presentation.ui.theme.Black
import com.paykidscompose.presentation.ui.theme.Blue1
import com.paykidscompose.presentation.ui.theme.Gray1
import com.paykidscompose.presentation.ui.theme.Gray6
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
    selected: AllowanceType,
    onSelect: (AllowanceType) -> Unit,
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
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            )
        ) {
            Column(
                modifier = Modifier
                    .width(PopupDialogCardColumnSize)
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 25.dp,
                        bottom = 25.dp
                    )
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "용돈기입하기",
                    style = AllowanceInputTitleTextStyle.copy(color = Black)
                )

                Spacer(Modifier.height(26.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(34.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(color = White)
                        .border(
                            width = 1.dp,
                            color = Gray6,
                            shape = RoundedCornerShape(100.dp)
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(100.dp))
                            .background(White)
                            .border(
                                width = 1.dp,
                                color = Gray6,
                                shape = RoundedCornerShape(100.dp)
                            )
                    )

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(100.dp))
                            .background(White)
                            .border(
                                width = 1.dp,
                                color = Gray6,
                                shape = RoundedCornerShape(100.dp)
                            )
                    )
                }
            }
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