package com.paykidscompose.presentation.screen.home.constants

import androidx.compose.ui.unit.dp
import com.paykidscompose.presentation.R

val stageImageSet = listOf(
    R.drawable.ic_home_pig_unlock to R.drawable.ic_home_pig_lock,
    R.drawable.ic_home_coin_unlock to R.drawable.ic_home_coin_lock,
    R.drawable.ic_home_card_unlock to R.drawable.ic_home_card_lock,
    R.drawable.ic_home_acount_unlock to R.drawable.ic_home_acount_lock,
    R.drawable.ic_home_moneybag_unlock to R.drawable.ic_home_moneybag_lock
)

// center 기준 스테이지 좌우 움직임 값
val StageOffsetPattern = listOf(-100, -45, 19, 71, 98, 98, 71, 19, -45, -100).map { it.dp }
val StageFirstItemOffset = (-73).dp