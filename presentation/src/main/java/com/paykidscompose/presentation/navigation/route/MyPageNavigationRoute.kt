package com.paykidscompose.presentation.navigation.route

import kotlinx.serialization.Serializable


@Serializable
sealed interface MyPageNavigationRoute {
    @Serializable
    data object MyInfoRoute: MyPageNavigationRoute

    @Serializable
    data object TermsPolicyRoute: MyPageNavigationRoute

}