package com.paykidscompose.presentation.navigation.route

import kotlinx.serialization.Serializable

@Serializable
sealed interface TabNavigationRoute {
    @Serializable
    data object HomeRoute: TabNavigationRoute

    @Serializable
    data object AllowanceDiaryRoute: TabNavigationRoute

    @Serializable
    data object QuestAchievementsRoute: TabNavigationRoute

    @Serializable
    data object MyPageRoute: TabNavigationRoute
}

fun TabNavigationRoute.toRoute(): String = when(this) {
    TabNavigationRoute.QuestAchievementsRoute -> "quest_achievements"
    TabNavigationRoute.AllowanceDiaryRoute -> "allowance_diary"
    TabNavigationRoute.HomeRoute -> "home"
    TabNavigationRoute.MyPageRoute -> "my_page"
}