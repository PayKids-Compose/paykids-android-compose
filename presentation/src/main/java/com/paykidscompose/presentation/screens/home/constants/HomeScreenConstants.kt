package com.paykidscompose.presentation.screens.home.constants

import androidx.compose.ui.unit.dp
import com.paykidscompose.presentation.R

val stageImageSet = listOf(
    R.drawable.ic_home_pig_unlock to R.drawable.ic_home_pig_lock,
    R.drawable.ic_home_coin_unlock to R.drawable.ic_home_coin_lock,
    R.drawable.ic_home_card_unlock to R.drawable.ic_home_card_lock,
    R.drawable.ic_home_acount_unlock to R.drawable.ic_home_acount_lock,
    R.drawable.ic_home_moneybag_unlock to R.drawable.ic_home_moneybag_lock
)

val StageStartPaddingPattern = listOf(35, 90, 154, 206, 233, 206, 154, 90, 35).map { it.dp }
val StageFirstItemStartPadding = 62.dp