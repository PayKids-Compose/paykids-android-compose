package com.paykidscompose.presentation.navigation.route

import kotlinx.serialization.Serializable


@Serializable
sealed interface EntryNavigationRoute {
    @Serializable
    data object SplashRoute: EntryNavigationRoute

    @Serializable
    data object LoginRoute: EntryNavigationRoute

    @Serializable
    data object LoginNicknameRoute: EntryNavigationRoute

    @Serializable
    data object MainTabRoute: EntryNavigationRoute
}

fun EntryNavigationRoute.toRoute(): String = when(this) {
    EntryNavigationRoute.SplashRoute -> "splash"
    EntryNavigationRoute.LoginRoute -> "login"
    EntryNavigationRoute.LoginNicknameRoute -> "login_nickname"
    EntryNavigationRoute.MainTabRoute -> "main_tab"
}