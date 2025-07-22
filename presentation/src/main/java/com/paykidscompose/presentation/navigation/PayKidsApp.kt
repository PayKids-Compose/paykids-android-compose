package com.paykidscompose.presentation.navigation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.paykidscompose.presentation.navigation.route.AllowanceDiaryNavigationRoute
import com.paykidscompose.presentation.navigation.route.EntryNavigationRoute
import com.paykidscompose.presentation.navigation.route.MyPageNavigationRoute
import com.paykidscompose.presentation.navigation.route.QuizNavigationRoute
import com.paykidscompose.presentation.navigation.route.TabNavigationRoute
import com.paykidscompose.presentation.screens.PayKidsScaffold
import com.paykidscompose.presentation.screens.allowance.AllowanceDiary
import com.paykidscompose.presentation.screens.allowance.analysis.ExpenseAnalysis
import com.paykidscompose.presentation.screens.allowance.detail.CategoryDetail
import com.paykidscompose.presentation.screens.home.Home
import com.paykidscompose.presentation.screens.login.Login
import com.paykidscompose.presentation.screens.login.LoginViewModel
import com.paykidscompose.presentation.screens.login.nickname.LoginNicknameViewModel
import com.paykidscompose.presentation.screens.login.nickname.Nickname
import com.paykidscompose.presentation.screens.mypage.MyPage
import com.paykidscompose.presentation.screens.mypage.MyPageViewModel
import com.paykidscompose.presentation.screens.mypage.info.MyInfo
import com.paykidscompose.presentation.screens.mypage.info.MyInfoViewModel
import com.paykidscompose.presentation.screens.mypage.terms.TermsPolicyScreen
import com.paykidscompose.presentation.screens.quest.QuestAndAchievement
import com.paykidscompose.presentation.screens.quiz.Quiz
import com.paykidscompose.presentation.screens.quiz.QuizClear
import com.paykidscompose.presentation.screens.quiz.QuizEntry
import com.paykidscompose.presentation.screens.study.Study
import com.paykidscompose.presentation.ui.components.AppBottomBar


@Composable
fun PayKidsApp(
    loginViewModel: LoginViewModel,
    loginNicknameViewModel: LoginNicknameViewModel,
    myInfoViewModel: MyInfoViewModel,
    myPageViewModel: MyPageViewModel
) {
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    // 다형성이 지원이 안되는 듯
//    val currentRoute =  navBackStackEntry?.toRoute<NavigationRoute>()
    val currentRoute = navBackStackEntry?.destination?.route

    Log.d("PayKidsApp", "CurrentRoute: $currentRoute")

    var isLogin by remember { mutableStateOf(false) }

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
            startDestination = if (isLogin) {
                TabNavigationRoute.HomeRoute
            } else {
                EntryNavigationRoute.LoginRoute
            },
            modifier = Modifier.padding(paddingValues = paddingValues),
        ) {

            composable<EntryNavigationRoute.LoginRoute> {
                Login(loginViewModel) {
                    navController.navigate(EntryNavigationRoute.LoginNicknameRoute)
                }
            }

            composable<EntryNavigationRoute.LoginNicknameRoute> {
                Nickname(loginNicknameViewModel) {
                    isLogin = true
                }
            }

            composable<TabNavigationRoute.HomeRoute> {
                Home { stageNumber ->
                    navController.navigate(
                        QuizNavigationRoute.QuizEntryRoute(stageNumber)
                    )
                }
            }

            composable<QuizNavigationRoute.QuizEntryRoute> { backStack ->
                val targetRoute = backStack.toRoute<QuizNavigationRoute.QuizEntryRoute>()
                val stageNumber = targetRoute.stageNumber

                QuizEntry(
                    stageNumber = stageNumber,
                    onQuiz = {
                        navController.navigate(QuizNavigationRoute.QuizRoute(stageNumber))
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

                Quiz(
                    stageNumber = stageNumber,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onConfirmClick = { clear ->
                        navController.navigate(QuizNavigationRoute.QuizClearRoute(clear))
                    }
                )
            }

            composable<QuizNavigationRoute.QuizClearRoute> { backStack ->
                val targetRoute = backStack.toRoute<QuizNavigationRoute.QuizClearRoute>()
                val clearType = targetRoute.clear

                QuizClear(
                    clearType = clearType,
                    onExitClick = {
                        navController.navigate(TabNavigationRoute.HomeRoute) {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                            // 백스택 맨 위에가 홈이라면 기존 화면을 재사용하기 위해서 넣음.
                        }
                    }
                )
            }

            composable<QuizNavigationRoute.StudyRoute> { backStack ->
                val targetRoute = backStack.toRoute<QuizNavigationRoute.StudyRoute>()
                val stageNumber = targetRoute.stageNumber

                Study(
                    stageNumber = stageNumber,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable<TabNavigationRoute.QuestAndAchievementRoute> {
                QuestAndAchievement()
            }

            composable<TabNavigationRoute.MyPageRoute> {
                MyPage(
                    myPageViewModel,
                    onClickMyInfo = {
                        navController.navigate(MyPageNavigationRoute.MyInfoRoute)
                    },
                    onClickTerms = {
                        navController.navigate(MyPageNavigationRoute.TermsPolicyRoute)
                    })
            }

            composable<MyPageNavigationRoute.MyInfoRoute> {
                MyInfo(
                    myInfoViewModel,
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
                    onCategoryExpense = {
                        navController.navigate(AllowanceDiaryNavigationRoute.ExpenseAnalysisRoute)
                    }
                )
            }

            composable<AllowanceDiaryNavigationRoute.ExpenseAnalysisRoute> {
                ExpenseAnalysis(
                    onCategoryCard = {
                        navController.navigate(AllowanceDiaryNavigationRoute.CategoryDetailRoute)
                    }
                )
            }

            composable<AllowanceDiaryNavigationRoute.CategoryDetailRoute> {
                CategoryDetail()
            }
        }
    }
}