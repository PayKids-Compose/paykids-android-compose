package com.paykidscompose.presentation.model

import com.paykidscompose.presentation.dummy.AllowanceChartDTO
import com.paykidscompose.presentation.model.type.AllowanceType
import com.paykidscompose.presentation.util.formatAmount

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
