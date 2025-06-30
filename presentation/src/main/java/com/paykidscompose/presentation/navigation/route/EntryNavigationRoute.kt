package com.paykidscompose.presentation.navigation.route

import kotlinx.serialization.Serializable


@Serializable
sealed interface EntryNavigationRoute: NavigationRoute {

    @Serializable
    data object LoginRoute: EntryNavigationRoute

    @Serializable
    data object LoginNicknameRoute: EntryNavigationRoute
//
//    @Serializable
//    data object MainTabRoute: EntryNavigationRoute
}