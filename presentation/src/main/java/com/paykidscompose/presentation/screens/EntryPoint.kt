package com.paykidscompose.presentation.screens

import android.R.attr.navigationIcon
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.paykidscompose.presentation.navigation.MainNavigationRoute
import com.paykidscompose.presentation.screens.allowance.AllowanceDiaryScreen
import com.paykidscompose.presentation.screens.bottom.BottomAppBarItem
import com.paykidscompose.presentation.screens.home.HomeScreen
import com.paykidscompose.presentation.screens.mypage.MyPageScreen
import com.paykidscompose.presentation.screens.quest.QuestAchievements
import com.paykidscompose.presentation.ui.theme.Blue1
import com.paykidscompose.presentation.ui.theme.BottomBarTextStyle
import com.paykidscompose.presentation.ui.theme.Transparent

@Composable
fun EntryPoint(){
    val navController = rememberNavController()

    val bottomAppBarItems = remember {
        BottomAppBarItem.fetchBottomAppBarItems()
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val routeName = navBackStackEntry?.destination?.route


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            /**
             * Bottom Bar 구성 하기
             */
            NavigationBar {
                bottomAppBarItems.forEachIndexed { _, bottomItem ->
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Blue1,
                            indicatorColor = Color.Unspecified
                        ),
                        selected = true,
                        label = {
                            Text(
                                text = bottomItem.tabName,
                                style = BottomBarTextStyle.copy(color = Blue1)
                            )
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = bottomItem.icon),
                                contentDescription = bottomItem.tabName,
                                tint = Color.Unspecified
                            )
                        },
                        onClick = {
                            navController.navigate(bottomItem.destination) {
                                popUpTo(bottomItem.destination) {
                                    saveState = true
                                }

                                launchSingleTop = true
                                restoreState = true
                            }
                        })
                }
            }
        }) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = MainNavigationRoute.HomeRoute, //홈을 시작 탭으로 설정
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
        ) {

            composable<MainNavigationRoute.QuestAchievementsRoute> {
                QuestAchievements()
            }
            composable<MainNavigationRoute.AllowanceDiaryRoute> {
                AllowanceDiaryScreen()
            }
            composable<MainNavigationRoute.HomeRoute> {
                HomeScreen()
            }
            composable<MainNavigationRoute.MyPageRoute> {
                MyPageScreen()
            }
        }
    }
}