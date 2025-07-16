package com.paykidscompose.common.model.allowance

import com.paykidscompose.common.enums.AllowanceType
import com.paykidscompose.common.model.Model

data class AllowanceChartCategoryModel(
    val type: AllowanceType,
    val category: String,
    val percent: Int,
    val amount: Int
) : Model()
