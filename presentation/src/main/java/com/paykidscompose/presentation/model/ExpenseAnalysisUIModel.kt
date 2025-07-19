package com.paykidscompose.presentation.model

import androidx.compose.ui.graphics.Color
import com.paykidscompose.common.enums.AllowanceType
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.presentation.base.UIState
import java.time.LocalDate


data class AnalysisUIModel(
    val name: String,
    val amount: Int,
    val percent: Float,
    val color: Color
)

data class ExpenseAnalysisUiState(
    override val isLoading: Boolean = false,
    override val error: PayKidsException? = null,
    val stats: List<AnalysisUIModel> = emptyList(),
    val showInputDialog: Boolean = false,
    val selectedType: AllowanceType = AllowanceType.EXPENSE,
    val currentMonth: LocalDate = LocalDate.now()
): UIState()