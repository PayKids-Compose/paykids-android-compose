package com.paykidscompose.presentation.model.allowance

import com.paykidscompose.common.enums.AllowanceType
import com.paykidscompose.presentation.model.UIModel
import java.time.LocalDate

data class AllowanceChartUIModel(
    val id: Long,
    val date: LocalDate,
    val type: AllowanceType,
    val category: String,
    val amountFormatted: String,
    val amount: Int,
    val memo: String
) : UIModel()