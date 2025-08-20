package com.paykidscompose.presentation.screen.quest

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.presentation.model.achievement.AchievementUIModel
import com.paykidscompose.presentation.model.quest.QuestUIModel
import com.paykidscompose.presentation.screen.quest.page.AchievementPage
import com.paykidscompose.presentation.screen.quest.page.QuestPage
import com.paykidscompose.presentation.ui.components.ScreenLoading
import com.paykidscompose.presentation.ui.theme.Blue1
import com.paykidscompose.presentation.ui.theme.Gray2
import com.paykidscompose.presentation.ui.theme.PayKidsComposeTheme
import com.paykidscompose.presentation.ui.theme.QuestAndAchievementTabHeight
import com.paykidscompose.presentation.ui.theme.QuestAndAchievementTabIndicatorHeight
import com.paykidscompose.presentation.ui.theme.QuestAndAchievementTabTextStyle
import com.paykidscompose.presentation.ui.theme.White
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun QuestAndAchievement(
    questAndAchievementViewModel: QuestAndAchievementViewModel = hiltViewModel()
) {
    val uiState by questAndAchievementViewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { QuestAndAchievementTab.entries.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            when (it) {
                is PayKidsException.SnackBarException -> {
                }

                is PayKidsException.DialogException -> {
                }

                is PayKidsException.ToastException -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }

            questAndAchievementViewModel.clearError()
        }
    }

    when {
        uiState.isLoading -> {
            ScreenLoading()
        }
        else -> {
            QuestAndAchievementScreen(
                quests = uiState.quests,
                achievements = uiState.achievements,
                onQuestRemove = { questAndAchievementViewModel.removeQuest(it) },
                scope = scope,
                pagerState = pagerState,
                selectedTabIndex = selectedTabIndex
            )
        }
    }
}

@Composable
fun QuestAndAchievementScreen(
    quests: List<QuestUIModel>,
    achievements: List<AchievementUIModel>,
    onQuestRemove: (String) -> Unit,
    scope: CoroutineScope,
    pagerState: PagerState,
    selectedTabIndex: State<Int>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(
                top = WindowInsets.statusBars.asPaddingValues()
                    .calculateTopPadding()
            )
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex.value,
            modifier = Modifier
                .fillMaxWidth(),
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex.value]),
                    height = QuestAndAchievementTabIndicatorHeight,
                    color = Blue1
                )
            },
            divider = {} // 하단 Divider 제거
        ) {
            QuestAndAchievementTab.entries.forEachIndexed { index, currentTab ->
                Tab(
                    modifier = Modifier
                        .height(QuestAndAchievementTabHeight)
                        .background(White),
                    selected = selectedTabIndex.value == index,
                    selectedContentColor = Blue1,
                    unselectedContentColor = Gray2,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(currentTab.ordinal)
                        }
                    },
                    text = {
                        Text(
                            text = stringResource(currentTab.titleResId),
                            style = QuestAndAchievementTabTextStyle
                        )
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()

        ) { index ->
            when (index) {
                0 -> QuestPage(
                    quests = quests,
                    onQuestRemove = onQuestRemove
                )
                1 -> AchievementPage(achievements)
            }
        }
    }

}

@Preview
@Composable
fun QuestAndAchievementScreenPreview() {
    PayKidsComposeTheme {
        QuestAndAchievement()
    }
}