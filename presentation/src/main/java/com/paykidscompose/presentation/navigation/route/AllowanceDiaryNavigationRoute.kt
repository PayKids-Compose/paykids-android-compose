package com.paykidscompose.presentation.navigation.route

import kotlinx.serialization.Serializable

@Serializable
sealed interface AllowanceDiaryNavigationRoute {
    @Serializable
    data object CategoryDetailRoute: AllowanceDiaryNavigationRoute

    @Serializable
    data object ExpenseAnalysisRoute: AllowanceDiaryNavigationRoute
}

fun AllowanceDiaryNavigationRoute.toRoute(): String = when(this) {
    AllowanceDiaryNavigationRoute.CategoryDetailRoute -> "category_detail"
    AllowanceDiaryNavigationRoute.ExpenseAnalysisRoute -> "expense_analysis"
}