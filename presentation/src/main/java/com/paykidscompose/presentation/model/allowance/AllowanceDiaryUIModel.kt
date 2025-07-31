package com.paykidscompose.presentation.model.allowance

import com.paykidscompose.presentation.model.UIModel
import java.time.LocalDate

data class AllowanceDiaryUIModel(
    val headerTotalExpense: String = "0",
    val mostCategoryExpense: AllowanceChartCategoryUIModel? = null,
    val monthlyDailyAmounts: Map<LocalDate, Pair<Int, Int>> = emptyMap(),
    val selectedDayTransactions: List<AllowanceChartUIModel> = emptyList()
) : UIModel()