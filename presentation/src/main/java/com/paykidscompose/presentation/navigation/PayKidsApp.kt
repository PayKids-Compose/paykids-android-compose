package com.paykidscompose.presentation.navigation

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.paykidscompose.common.enums.EntryPoint
import com.paykidscompose.presentation.navigation.route.AllowanceDiaryNavigationRoute
import com.paykidscompose.presentation.navigation.route.EntryNavigationRoute
import com.paykidscompose.presentation.navigation.route.MyPageNavigationRoute
import com.paykidscompose.presentation.navigation.route.QuizNavigationRoute
import com.paykidscompose.presentation.navigation.route.TabNavigationRoute
import com.paykidscompose.presentation.screen.PayKidsScaffold
import com.paykidscompose.presentation.screen.allowance.AllowanceDiary
import com.paykidscompose.presentation.screen.allowance.analysis.TransactionAnalysis
import com.paykidscompose.presentation.screen.allowance.detail.CategoryDetail
import com.paykidscompose.presentation.screen.home.Home
import com.paykidscompose.presentation.screen.login.Login
import com.paykidscompose.presentation.screen.login.nickname.Nickname
import com.paykidscompose.presentation.screen.mypage.MyPage
import com.paykidscompose.presentation.screen.mypage.info.MyInfo
import com.paykidscompose.presentation.screen.mypage.terms.TermsPolicyScreen
import com.paykidscompose.presentation.screen.quest.QuestAndAchievement
import com.paykidscompose.presentation.screen.quiz.Quiz
import com.paykidscompose.presentation.screen.quiz.clear.QuizClear
import com.paykidscompose.presentation.screen.quiz.entry.QuizEntry
import com.paykidscompose.presentation.screen.study.Study
import com.paykidscompose.presentation.ui.components.AppBottomBar


@Composable
fun PayKidsApp(
    loginStatus: EntryPoint
) {
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    // 다형성이 지원이 안되는 듯
//    val currentRoute =  navBackStackEntry?.toRoute<NavigationRoute>()
    val currentRoute = navBackStackEntry?.destination?.route

    Log.d("PayKidsApp", "CurrentRoute: $currentRoute")

    PayKidsScaffold(
        bottomBar = {
            AppBottomBar(
                currentRoute = currentRoute,
                onNavigate = { bottomItemRoute ->
                    navController.navigate(bottomItemRoute) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            // 탭을 할 때 시작 목적지 전까지 스택을 비우기 위해 popUpTo 사용
                            // 백스택 깊이 최소화
                            saveState = true // 목적지의 상태를 저장
                        }

                        launchSingleTop = true // 바텀바에서 계속 탭을 해서 스택이 계속 쌓이는걸 방지하기 위해 사용함.
                        restoreState = true // 목적지가 백스택에 있으면 저장된 상태를 복원
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination =
                when (loginStatus) {
                    EntryPoint.LOGIN -> EntryNavigationRoute.LoginRoute
                    EntryPoint.ONBOARDING -> EntryNavigationRoute.LoginNicknameRoute
                    EntryPoint.HOME -> TabNavigationRoute.HomeRoute
                },
            modifier = Modifier.padding(paddingValues = paddingValues),
            enterTransition = { fadeIn(animationSpec = tween(800)) },
            exitTransition = { fadeOut(animationSpec = tween(800)) }
        ) {

            composable<EntryNavigationRoute.LoginRoute> {
                Login(hiltViewModel())
            }

            composable<EntryNavigationRoute.LoginNicknameRoute> {
                Nickname(hiltViewModel())
            }

            composable<TabNavigationRoute.HomeRoute> {
                Home { stageNumber, stageTitle ->
                    navController.navigate(
                        QuizNavigationRoute.QuizEntryRoute(stageNumber, stageTitle)
                    )
                }
            }

            composable<QuizNavigationRoute.QuizEntryRoute> { backStack ->
                val targetRoute = backStack.toRoute<QuizNavigationRoute.QuizEntryRoute>()
                val stageNumber = targetRoute.stageNumber
                val stageTitle = targetRoute.stageTitle

                QuizEntry(
                    stageNumber = stageNumber,
                    stageTitle = stageTitle,
                    onQuiz = {
                        navController.navigate(QuizNavigationRoute.QuizRoute(stageNumber, false))
                    },
                    onWrongNote = {
                        navController.navigate(QuizNavigationRoute.QuizRoute(stageNumber, true))
                    },
                    onStudyClick = {
                        navController.navigate(QuizNavigationRoute.StudyRoute(stageNumber))
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable<QuizNavigationRoute.QuizRoute> { backStack ->
                val targetRoute = backStack.toRoute<QuizNavigationRoute.QuizRoute>()
                val stageNumber = targetRoute.stageNumber
                val isWrongAnswerNote = targetRoute.isWrongAnswerNote

                Quiz(
                    stageNumber = stageNumber,
                    isWrongAnswerNote = isWrongAnswerNote,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onConfirmClick = { clear ->
                        navController.navigate(QuizNavigationRoute.QuizClearRoute(stageNumber, clear))
                    }
                )
            }

            composable<QuizNavigationRoute.QuizClearRoute> { backStack ->
                val targetRoute = backStack.toRoute<QuizNavigationRoute.QuizClearRoute>()
                val stageNumber = targetRoute.stageNumber
                val clearType = targetRoute.clear

                QuizClear(
                    clearType = clearType,
                    onExitClick = {
//                        navController.navigate(TabNavigationRoute.HomeRoute) {
//                            popUpTo(navController.graph.findStartDestination().id)
//                            launchSingleTop = true
//                            // 백스택 맨 위에가 홈이라면 기존 화면을 재사용하기 위해서 넣음.
//                        }
                        navController.popBackStack(TabNavigationRoute.HomeRoute, inclusive = false)
                    },
                    onWrongNoteClick = {
                        navController.navigate(QuizNavigationRoute.QuizRoute(stageNumber, true))
                    }
                )
            }

            composable<QuizNavigationRoute.StudyRoute> { backStack ->
                val targetRoute = backStack.toRoute<QuizNavigationRoute.StudyRoute>()
                val stageNumber = targetRoute.stageNumber

                Study(
                    stageNumber = stageNumber,
                    onBackClick = {
                        navController.navigateUp()
                    }
                )
            }

            composable<TabNavigationRoute.QuestAndAchievementRoute> {
                QuestAndAchievement()
            }

            composable<TabNavigationRoute.MyPageRoute> {
                MyPage(
                    hiltViewModel(),
                    onClickMyInfo = {
                        navController.navigate(MyPageNavigationRoute.MyInfoRoute)
                    },
                    onClickTerms = {
                        navController.navigate(MyPageNavigationRoute.TermsPolicyRoute)
                    })
            }

            composable<MyPageNavigationRoute.MyInfoRoute> {
                MyInfo(
                    hiltViewModel(),
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable<MyPageNavigationRoute.TermsPolicyRoute> {
                TermsPolicyScreen(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable<TabNavigationRoute.AllowanceDiaryRoute> {
                AllowanceDiary(
                    viewModel = hiltViewModel(),
                    onCategoryExpense = {
                        navController.navigate(AllowanceDiaryNavigationRoute.ExpenseAnalysisRoute)
                    }
                )
            }

            composable<AllowanceDiaryNavigationRoute.ExpenseAnalysisRoute> {
                TransactionAnalysis(
                    viewModel = hiltViewModel(),
                    onCategoryCard = { year, month, category, type ->
                        navController.navigate(
                            AllowanceDiaryNavigationRoute.CategoryDetailRoute(
                                year,
                                month,
                                category,
                                type
                            )
                        )
                    }
                )
            }

            composable<AllowanceDiaryNavigationRoute.CategoryDetailRoute> {
                val targetRoute = it.toRoute<AllowanceDiaryNavigationRoute.CategoryDetailRoute>()
                val year = targetRoute.year
                val month = targetRoute.month
                val category = targetRoute.category
                val type = targetRoute.type

                CategoryDetail(year, month, category, type, hiltViewModel())
            }
        }
    }
}