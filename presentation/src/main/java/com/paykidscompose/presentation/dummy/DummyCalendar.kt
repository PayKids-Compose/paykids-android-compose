package com.paykidscompose.presentation.dummy

import com.paykidscompose.presentation.model.AllowanceType


data class AllowanceChartCategoryDto( // 편의점 -1,800 50%
    val allowanceType: AllowanceType,
    val category: String,
    val percent: String,
    val amount: Int
)

data class AllowanceChartAmountDTO(
    val date: String, // yyyy-dd-mm
    val amount : Int,
    val allowanceType: AllowanceType
)

data class CategoryDTO(
    val id: Int,
    val title: String,
    val allowanceType: AllowanceType
)

data class AllowanceChartDTO(
    val id: Int,
    val date: String,
    val allowanceType: AllowanceType,
    val category: String,
    val amount: Int,
    val memo: String
)

