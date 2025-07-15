package com.paykidscompose.common.model.allowance

import com.paykidscompose.common.model.AllowanceType
import java.time.LocalDate

data class AllowanceChartModel(
    val id: Long,
    val date: LocalDate,
    val type: AllowanceType,
    val category: String,
    val amount: Int,
    val memo: String
)