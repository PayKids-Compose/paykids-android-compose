package com.paykidscompose.presentation.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.paykidscompose.presentation.R

val NanumSquare = FontFamily(
    Font(R.font.nanumsquare_extra_bold, FontWeight.ExtraBold),
    Font(R.font.nanumsquare_bold, FontWeight.Bold),
    Font(R.font.nanumsquare_regular, FontWeight.Normal),
    Font(R.font.nanumsquare_light, FontWeight.Light)
)

// 작게 나오는 텍스트
val InfoTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.Bold,
    fontSize = 12.sp,
    lineHeight = 14.sp
)

// 홈 스크린
val StageCardNumberTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 12.sp,
    lineHeight = 14.sp,
    color = Blue1
)
val StageCardTitleTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 18.sp,
    lineHeight = 20.sp,
    color = Black
)

val StageTooltipTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 22.sp,
    lineHeight = 25.sp
)

// 닉네임 스크린
val NicknameTitleTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 24.sp,
    lineHeight = 27.sp
)

val NicknameFieldTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp,
    lineHeight = 20.sp
)

// 결정하기 버튼
val DeterminationTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 20.sp,
    lineHeight = 23.sp
)

val DeterminationTextStyle2 = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 18.sp,
    lineHeight = 20.sp
)

// 퀴즈 진입 스크린
val StageNumberCardTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 14.sp,
    lineHeight = 16.sp,
    color = Gray8
)

// 퀴즈 스크린
val QuizAppBarTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 16.sp,
    lineHeight = 18.sp
)

val QuizQuestionTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 22.sp,
    lineHeight = 25.sp
)

val QuizAnswerTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp,
    lineHeight = 20.sp
)

val QuizResultCardTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 16.sp,
    lineHeight = 18.sp,
    color = White
)

val QuizShortAnswerTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 22.sp,
    lineHeight = 25.sp
)

// 퀴즈 클리어 스크린
val QuizClearTitleTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 18.sp,
    lineHeight = 26.sp
)

val QuizClearButtonTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 20.sp,
    lineHeight = 23.sp
)

// 마이페이지 스크린
val MyPageAppBarTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 18.sp,
    lineHeight = 20.sp
)

val MyPageNicknameTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 22.sp,
    lineHeight = 25.sp
)

val MyPageCardTitleTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp,
    lineHeight = 18.sp
)

// 내 정보 스크린
val MyInfoCardTitleTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 18.sp,
    lineHeight = 20.sp
)

val MyInfoCardNicknameTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 16.sp,
    lineHeight = 18.sp
)

val MyInfoCardNicknameButtonTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp,
    lineHeight = 26.sp
)

val MyInfoCardUserDeleteTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp,
    lineHeight = 26.sp
)

// 약관 및 정책 스크린
val TermsPolicyInfoTitleTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp,
    lineHeight = 16.sp
)
val TermsPolicyCardItemTitleTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp,
    lineHeight = 18.sp
)

// 팝업 다이얼로그
val PopupDialogTitleTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 16.sp,
    lineHeight = 18.sp
)
val PopupDialogTitleInfoTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.Bold,
    fontSize = 12.sp,
    lineHeight = 26.sp
)
val PopupDialogButtonTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 14.sp,
    lineHeight = 16.sp
)

// 용돈기입하기 다이얼로그
val AllowanceInputTitleTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 18.sp,
    lineHeight = 20.sp
)
val AllowanceInputToggleTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 14.sp,
    lineHeight = 14.sp
)
val AllowanceInputItemTitleTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 16.sp,
    lineHeight = 18.sp
)

val AllowanceInputDateAndConfirmButtonTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 14.sp,
    lineHeight = 16.sp
)

val AllowanceInputItemTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp,
    lineHeight = 18.sp
)

// 용돈일기 스크린

val AllowanceDiaryTitleExpenseTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 20.sp,
    lineHeight = 23.sp
)
val AllowanceDiaryMostConsumeTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.Bold,
    fontSize = 11.sp,
    lineHeight = 12.sp
)
val AllowanceDiaryMostConsumeTitleTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 16.sp,
    lineHeight = 18.sp
)
val AllowanceDiaryDetailConsumeTitleTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 18.sp,
    lineHeight = 20.sp
)
val AllowanceDiaryDetailConsumeMonthDayTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 12.sp,
    lineHeight = 14.sp
)
val AllowanceDiaryHeadMonthTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 16.sp,
    lineHeight = 18.sp
)
val AllowanceDiaryCalendarDayTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp,
    lineHeight = 26.sp,
)
val AllowanceDiaryCalendarIncomeConsumeTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 10.sp,
    lineHeight = 13.sp
)

val AllowanceDiaryCalendarWeekTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp,
    lineHeight = 26.sp
)

// 용돈일기 지출분석 스크린
val ExpenseAnalysisDeleteButtonTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp,
    lineHeight = 26.sp
)
val ExpenseAnalysisItemCategoryTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp,
    lineHeight = 26.sp
)
val ExpenseAnalysisItemAmountTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 20.sp,
    lineHeight = 28.sp
)
val ExpenseAnalysisItemPercentTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp,
    lineHeight = 26.sp
)
val ExpenseAnalysisItemAddButtonTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 16.sp,
    lineHeight = 18.sp
)

// 카테고리 세부내역 스크린
val CategoryDetailTitleTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 20.sp,
    lineHeight = 23.sp
)
val CategoryDetailItemCategoryTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.Bold,
    fontSize = 11.sp,
    lineHeight = 12.sp
)
val CategoryDetailItemAmountTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 16.sp,
    lineHeight = 18.sp
)
val CategoryDetailItemMemoTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.Bold,
    fontSize = 11.sp,
    lineHeight = 12.sp
)
val CategoryDetailItemAddTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 16.sp,
    lineHeight = 18.sp
)
// 바텀바 폰트
val BottomBarTextStyle = TextStyle(
    fontFamily = NanumSquare,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 9.sp,
    lineHeight = 13.sp
)