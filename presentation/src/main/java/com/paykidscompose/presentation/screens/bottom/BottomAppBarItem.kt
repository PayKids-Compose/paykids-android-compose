package com.paykidscompose.presentation.screens.bottom

import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.navigation.MainNavigationRoute


data class BottomAppBarItem(
    val tabName: String = "",
    val icon: Int = R.drawable.ic_home,
    val destination: MainNavigationRoute = MainNavigationRoute.HomeRoute
) {
    companion object {
        fun fetchBottomAppBarItems() = listOf(
            BottomAppBarItem(
                tabName = "퀘스트",
                icon = R.drawable.ic_quest,
                destination = MainNavigationRoute.QuestAchievementsRoute
            ),
            BottomAppBarItem(
                tabName = "용돈일기",
                icon = R.drawable.ic_diary,
                destination = MainNavigationRoute.AllowanceDiaryRoute
            ),
            BottomAppBarItem(
                tabName = "홈",
                icon = R.drawable.ic_home,
                destination = MainNavigationRoute.HomeRoute
            ),
            BottomAppBarItem(
                tabName = "마이",
                icon = R.drawable.ic_mypage,
                destination = MainNavigationRoute.MyPageRoute
            ),

            )
    }

}