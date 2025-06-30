package com.paykidscompose.presentation.navigation.route

import kotlinx.serialization.Serializable

@Serializable
sealed interface AllowanceDiaryNavigationRoute: NavigationRoute {
    @Serializable
    data object CategoryDetailRoute: AllowanceDiaryNavigationRoute

    @Serializable
    data object ExpenseAnalysisRoute: AllowanceDiaryNavigationRoute
}