package com.paykidscompose.presentation.navigation.bottom

import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.navigation.route.TabNavigationRoute


data class BottomBarItem(
    val tabName: String,
    val icon: Int,
    val destination: TabNavigationRoute
) {
    companion object {
        fun fetchBottomAppBarItems() = listOf(
            BottomBarItem(
                tabName = "퀘스트",
                icon = R.drawable.ic_quest,
                destination = TabNavigationRoute.QuestAchievementsRoute
            ),
            BottomBarItem(
                tabName = "용돈일기",
                icon = R.drawable.ic_diary,
                destination = TabNavigationRoute.AllowanceDiaryRoute
            ),
            BottomBarItem(
                tabName = "홈",
                icon = R.drawable.ic_home,
                destination = TabNavigationRoute.HomeRoute
            ),
            BottomBarItem(
                tabName = "마이",
                icon = R.drawable.ic_mypage,
                destination = TabNavigationRoute.MyPageRoute
            ),
        )
    }
}