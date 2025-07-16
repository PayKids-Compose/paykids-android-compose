package com.paykidscompose.common.model.allowance

import com.paykidscompose.common.enums.AllowanceType
import com.paykidscompose.common.model.Model
import java.time.LocalDate

data class AllowanceChartModel(
    val id: Long,
    val date: LocalDate,
    val type: AllowanceType,
    val category: String,
    val amount: Int,
    val memo: String
) : Model()