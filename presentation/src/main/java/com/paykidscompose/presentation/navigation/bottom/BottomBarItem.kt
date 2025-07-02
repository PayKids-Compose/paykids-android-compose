package com.paykidscompose.presentation.navigation.bottom

import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.navigation.route.AllowanceDiaryNavigationRoute
import com.paykidscompose.presentation.navigation.route.NavigationRoute
import com.paykidscompose.presentation.navigation.route.TabNavigationRoute
import com.paykidscompose.presentation.navigation.route.serialName


data class BottomBarItem(
    val tabName: String,
    val icon: Int,
    val destination: TabNavigationRoute,
    val relatedRoutes: List<NavigationRoute> = listOf(destination)
) {
    fun isSelected(currentRoute: String?): Boolean {
        if (currentRoute == null) return false
        // 현재 루트를 문자열로 받기 때문에 관련 경로도 문자열로 변환해서 비교 진행
        val relatedRouteNames = relatedRoutes.map { it.serialName() }
        return currentRoute in relatedRouteNames
    }

    companion object {
        const val QUEST = "퀘스트"
        const val ALLOWANCE_DIARY = "용돈일기"
        const val HOME = "홈"
        const val MY = "마이"

        val fetchBottomAppBarItems = listOf(
            BottomBarItem(
                tabName = QUEST,
                icon = R.drawable.ic_quest_tab,
                destination = TabNavigationRoute.QuestAchievementsRoute
            ),
            BottomBarItem(
                tabName = ALLOWANCE_DIARY,
                icon = R.drawable.ic_diary,
                destination = TabNavigationRoute.AllowanceDiaryRoute,
                relatedRoutes = listOf(
                    TabNavigationRoute.AllowanceDiaryRoute,
                    AllowanceDiaryNavigationRoute.ExpenseAnalysisRoute,
                    AllowanceDiaryNavigationRoute.CategoryDetailRoute
                )
            ),
            BottomBarItem(
                tabName = HOME,
                icon = R.drawable.ic_home,
                destination = TabNavigationRoute.HomeRoute
            ),
            BottomBarItem(
                tabName = MY,
                icon = R.drawable.ic_mypage,
                destination = TabNavigationRoute.MyPageRoute
            ),
        )
    }
}