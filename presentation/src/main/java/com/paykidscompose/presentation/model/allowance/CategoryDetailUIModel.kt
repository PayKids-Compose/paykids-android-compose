package com.paykidscompose.presentation.model.allowance

import com.paykidscompose.common.enums.AllowanceType
import com.paykidscompose.presentation.model.UIModel

data class CategoryDetailUIModel(
    val year: Int = -1,
    val month: Int = -1,
    val totalAmount: Int = 0,
    val category: String = "",
    val allowanceType: AllowanceType = AllowanceType.EXPENSE,
    val selectedTransaction: AllowanceChartUIModel? = null,
    val detailCategories: List<AllowanceChartUIModel> = emptyList()
): UIModel()