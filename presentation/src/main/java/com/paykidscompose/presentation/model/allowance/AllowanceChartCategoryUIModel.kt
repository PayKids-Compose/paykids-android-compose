package com.paykidscompose.presentation.model.allowance

import com.paykidscompose.common.enums.AllowanceType
import com.paykidscompose.presentation.model.UIModel

data class AllowanceChartCategoryUIModel(
    val type: AllowanceType,
    val category: String,
    val percent: Int,
    val amount: Int
) : UIModel()