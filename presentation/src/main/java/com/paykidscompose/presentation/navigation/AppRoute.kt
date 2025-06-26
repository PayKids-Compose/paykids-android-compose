package com.paykidscompose.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface MainNavigationRoute {

    // 인자가 없는 화면
    @Serializable
    data object SplashRoute: MainNavigationRoute

    @Serializable
    data object LoginRoute: MainNavigationRoute

    @Serializable
    data object LoginNicknameRoute: MainNavigationRoute

    @Serializable
    data object HomeRoute: MainNavigationRoute

    @Serializable
    data object MyInfoRoute: MainNavigationRoute

    @Serializable
    data object TermsPolicyRoute: MainNavigationRoute

    @Serializable
    data object MyPageRoute: MainNavigationRoute

    @Serializable
    data object AllowanceDiaryRoute: MainNavigationRoute

    @Serializable
    data object CategoryDetailRoute: MainNavigationRoute

    @Serializable
    data object ExpenseAnalysisRoute: MainNavigationRoute

    @Serializable
    data object QuestAchievementsRoute: MainNavigationRoute

    // 인자가 있는 화면
    @Serializable
    data class QuizEntryRoute(val stageNumber: Int) : MainNavigationRoute

    @Serializable
    data class QuizRoute(val stageNumber: Int): MainNavigationRoute
}