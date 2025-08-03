package com.paykidscompose.presentation.model.allowance

import com.paykidscompose.presentation.model.UIModel

data class TransactionAnalysisUIModel(
    val transactionList: List<AllowanceChartCategoryUIModel> = emptyList()
): UIModel()