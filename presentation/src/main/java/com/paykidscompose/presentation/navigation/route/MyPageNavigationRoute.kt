package com.paykidscompose.presentation.navigation.route

import kotlinx.serialization.Serializable


@Serializable
sealed interface MyPageNavigationRoute {
    @Serializable
    data object MyInfoRoute: MyPageNavigationRoute

    @Serializable
    data object TermsPolicyRoute: MyPageNavigationRoute

}

fun MyPageNavigationRoute.toRoute(): String = when(this) {
    MyPageNavigationRoute.MyInfoRoute -> "my_info"
    MyPageNavigationRoute.TermsPolicyRoute -> "terms_policy"
}