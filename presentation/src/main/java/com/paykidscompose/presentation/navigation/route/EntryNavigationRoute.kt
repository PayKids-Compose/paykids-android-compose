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