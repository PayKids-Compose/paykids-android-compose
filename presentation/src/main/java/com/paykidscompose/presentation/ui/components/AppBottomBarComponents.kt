package com.paykidscompose.presentation.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.paykidscompose.presentation.navigation.bottom.BottomBarItem
import com.paykidscompose.presentation.navigation.route.EntryNavigationRoute
import com.paykidscompose.presentation.navigation.route.TabNavigationRoute
import com.paykidscompose.presentation.navigation.route.toRoute
import com.paykidscompose.presentation.ui.theme.Blue1
import com.paykidscompose.presentation.ui.theme.BottomBarTextStyle
import com.paykidscompose.presentation.ui.theme.Gray6
import com.paykidscompose.presentation.ui.theme.Transparent
import com.paykidscompose.presentation.ui.theme.White

// 정적인 컴포넌트

@Composable
fun AppBottomBar(
    navController: NavHostController
) {
    val bottomAppBarItems = remember {
        BottomBarItem.fetchBottomAppBarItems()
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    // 터치 할 때 리플 수정 할 수 있으면 하기
    NavigationBar(
        modifier = Modifier.clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)),
        containerColor = White
    ) {
        bottomAppBarItems.forEach { bottomItem ->
            NavigationBarItem(
                selected = currentRoute == bottomItem.destination.toRoute(),
                label = {
                    Text(
                        text = bottomItem.tabName,
                        style = BottomBarTextStyle
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(id = bottomItem.icon),
                        contentDescription = bottomItem.tabName
                    )
                },
                onClick = {
                    navController.navigate(bottomItem.destination.toRoute()) {
                        popUpTo(EntryNavigationRoute.MainTabRoute.toRoute()) {
                            inclusive = false
                            saveState = true // 목적지의 상태를 저장
                        }

                        launchSingleTop = true // 바텀바에서 계속 탭을 해서 스택이 계속 쌓이는걸 방지하기 위해 사용함.
                        restoreState = true // 목적지가 백스택에 있으면 저장된 상태를 복원
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Blue1,
                    selectedTextColor = Blue1,
                    unselectedTextColor = Gray6,
                    unselectedIconColor = Gray6,
                    indicatorColor = Transparent
                ))
        }
    }
}

@Preview
@Composable
fun BottomBarPreview(){
    AppBottomBar(rememberNavController())
}