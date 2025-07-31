package com.paykidscompose.presentation.model.allowance

import com.paykidscompose.common.enums.AllowanceType
import com.paykidscompose.presentation.model.UIModel
import java.time.LocalDate

data class AllowanceChartAmountUIModel(
    val date: LocalDate,
    val amount: Int,
    val type: AllowanceType
) : UIModel()
