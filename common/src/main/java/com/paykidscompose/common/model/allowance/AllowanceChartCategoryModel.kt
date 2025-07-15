package com.paykidscompose.common.model.allowance

import com.paykidscompose.common.model.AllowanceType

data class AllowanceChartCategoryModel(
    val type: AllowanceType,
    val category: String,
    val percent: Int,
    val amount: Int
)
