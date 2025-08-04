package com.paykidscompose.presentation.navigation.route

import com.paykidscompose.common.enums.AllowanceType
import kotlinx.serialization.Serializable

@Serializable
sealed interface AllowanceDiaryNavigationRoute : NavigationRoute {
    @Serializable
    data class CategoryDetailRoute(
        val year: Int = -1,
        val month: Int = -1,
        val category: String = "",
        val type: AllowanceType = AllowanceType.EXPENSE
    ) : AllowanceDiaryNavigationRoute

    @Serializable
    data object ExpenseAnalysisRoute : AllowanceDiaryNavigationRoute
}