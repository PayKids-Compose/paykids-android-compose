package com.paykidscompose.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.paykidscompose.presentation.R

// FontFamily 정의
val NanumSquareExtraBold = FontFamily(Font(R.font.nanumsquare_extra_bold, FontWeight.ExtraBold))
val NanumSquareBold = FontFamily(Font(R.font.nanumsquare_bold, FontWeight.Bold))
val NanumSquareRegular = FontFamily(Font(R.font.nanumsquare_regular, FontWeight.Normal))
val NanumSquareLight = FontFamily(Font(R.font.nanumsquare_light, FontWeight.Light))

// Typography 정의
val typography = Typography(
    // heading3 → titleLarge (보통 22sp)
    titleLarge = TextStyle(
        fontFamily = NanumSquareExtraBold,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 22.sp,
        lineHeight = 22.sp
    ),

    // heading4_EB → titleMedium (18sp)
    titleMedium = TextStyle(
        fontFamily = NanumSquareExtraBold,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 18.sp,
        lineHeight = 20.sp
    ),

    // body1_EB → bodyLarge (20sp)
    bodyLarge = TextStyle(
        fontFamily = NanumSquareExtraBold,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp,
        lineHeight = 22.sp
    ),

    // body2_B → bodyMedium (16sp)
    bodyMedium = TextStyle(
        fontFamily = NanumSquareBold,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),

    // body3_EB → bodySmall (12sp)
    bodySmall = TextStyle(
        fontFamily = NanumSquareExtraBold,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 12.sp,
        lineHeight = 14.sp
    ),

    // text_bold_12px → labelSmall
    labelSmall = TextStyle(
        fontFamily = NanumSquareBold,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 22.sp
    ),

    // text_bold_14px → labelMedium
    labelMedium = TextStyle(
        fontFamily = NanumSquareBold,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 22.sp
    ),

    // button_bold_16px → labelLarge
    labelLarge = TextStyle(
        fontFamily = NanumSquareBold,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 22.sp
    )
)