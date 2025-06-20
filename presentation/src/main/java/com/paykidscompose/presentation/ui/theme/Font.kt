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