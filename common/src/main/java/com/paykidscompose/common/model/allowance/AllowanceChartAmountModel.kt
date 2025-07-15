package com.paykidscompose.common.model.allowance

import com.paykidscompose.common.model.AllowanceType
import java.time.LocalDate

data class AllowanceChartAmountModel(
    val date: LocalDate,
    val amount: Int,
    val type: AllowanceType
)
