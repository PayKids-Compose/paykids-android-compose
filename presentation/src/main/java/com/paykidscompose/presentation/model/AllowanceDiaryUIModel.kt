package com.paykidscompose.presentation.model

import com.paykidscompose.presentation.base.UIState
import com.paykidscompose.presentation.dummy.AllowanceChartDTO
import com.paykidscompose.presentation.model.type.AllowanceType
import com.paykidscompose.presentation.util.formatAmount
import java.time.LocalDate

data class AllowanceDiaryUIModel(
    val id: Int,
    val date: String,
    val category: String,
    val amountFormatted: String,
    val amount: Int,
    val memo: String,
    val allowanceType: AllowanceType
): UIModel()

fun AllowanceChartDTO.toUIModel(): AllowanceDiaryUIModel {
    val formattedAmount = when (allowanceType) {
        AllowanceType.EXPENSE -> "-${formatAmount(amount)}원"
        AllowanceType.INCOME -> "+${formatAmount(amount)}원"
    }

    return AllowanceDiaryUIModel(
        id = id,
        date = date,
        category = category,
        amountFormatted = formattedAmount,
        amount = amount,
        memo = memo,
        allowanceType = allowanceType
    )
}

data class AllowanceDiaryUIState(
    override val isLoading: Boolean = false,
    override val error: String? = null,
    val currentMonth: LocalDate = LocalDate.now().withDayOfMonth(1),
    val selectedDate: LocalDate = LocalDate.now(),
    val selectedUIModels: List<AllowanceDiaryUIModel> = emptyList(),
    val totalExpense: Int = 0,
    val dailySummary: Map<LocalDate, Pair<Int, Int>> = emptyMap(),
    val maxExpenseCategoryAndAmount: Pair<String, Int>? = null,
    val showInputDialog: Boolean = false
): UIState()