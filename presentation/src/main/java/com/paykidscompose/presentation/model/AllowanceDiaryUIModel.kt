package com.paykidscompose.presentation.model

import com.paykidscompose.presentation.dummy.AllowanceType


enum class AllowanceType{
    INCOME, // 수입
    EXPENSE // 소비
}

data class AllowanceDiaryUIModel(
    val allowanceType: AllowanceType,
    val category: String,
    val amount: String,
    val date: String,
)
