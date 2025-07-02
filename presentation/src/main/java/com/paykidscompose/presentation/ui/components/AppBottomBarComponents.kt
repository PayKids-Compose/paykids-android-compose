package com.paykidscompose.presentation.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paykidscompose.presentation.navigation.bottom.BottomBarItem
import com.paykidscompose.presentation.navigation.route.NavigationRoute
import com.paykidscompose.presentation.navigation.route.serialName
import com.paykidscompose.presentation.ui.theme.Blue1
import com.paykidscompose.presentation.ui.theme.BottomBarTextStyle
import com.paykidscompose.presentation.ui.theme.Gray6
import com.paykidscompose.presentation.ui.theme.Transparent
import com.paykidscompose.presentation.ui.theme.White

@Composable
fun AppBottomBar(
    currentRoute: String?,
    onNavigate: (NavigationRoute) -> Unit = {}
) {
    val (bottomBarItems, bottomRoutes) = remember {
        val items = BottomBarItem.fetchBottomAppBarItems
        val routes = items.flatMap { it.relatedRoutes }
            .map { it.serialName() }
            .distinct()
        items to routes
    }

    if (currentRoute !in bottomRoutes) return

    // 터치 할 때 리플 수정 할 수 있으면 하기
    NavigationBar(
        modifier = Modifier.clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)),
        containerColor = White
    ) {
        bottomBarItems.forEach { bottomItem ->
            NavigationBarItem(
                selected = bottomItem.isSelected(currentRoute),
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
                    onNavigate(bottomItem.destination)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Blue1,
                    selectedTextColor = Blue1,
                    unselectedTextColor = Gray6,
                    unselectedIconColor = Gray6,
                    indicatorColor = Transparent
                )
            )
        }
    }
}

@Preview
@Composable
fun BottomBarPreview() {
}