package com.paykidscompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.paykidscompose.presentation.navigation.route.EntryNavigationRoute
import com.paykidscompose.presentation.navigation.route.toRoute
import com.paykidscompose.presentation.screens.login.LoginScreen
import com.paykidscompose.presentation.screens.login.nickname.NicknameScreen
import com.paykidscompose.presentation.screens.splash.SplashScreen

@Composable
fun PayKidsApp(
) {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = EntryNavigationRoute.SplashRoute.toRoute()
    ) {
        composable(
            EntryNavigationRoute.SplashRoute.toRoute()
        ) {
            SplashScreen {
                navController.navigate(EntryNavigationRoute.LoginRoute.toRoute()) {
                    popUpTo(EntryNavigationRoute.SplashRoute.toRoute()) {
                        inclusive = true
                    }
                }
            }
        }

        composable(
            EntryNavigationRoute.LoginRoute.toRoute()
        ) {
            LoginScreen {
                navController.navigate(EntryNavigationRoute.LoginNicknameRoute.toRoute())
            }
        }

        composable(
            EntryNavigationRoute.LoginNicknameRoute.toRoute()
        ) {
            NicknameScreen {
                navController.navigate(EntryNavigationRoute.MainTabRoute.toRoute())
            }
        }

        composable(
            EntryNavigationRoute.MainTabRoute.toRoute()
        ) {
            TabNavGraph()
        }
    }
}