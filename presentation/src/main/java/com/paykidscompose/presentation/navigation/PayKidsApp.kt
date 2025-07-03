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
import com.paykidscompose.presentation.screens.allowance.AllowanceDiaryScreen
import com.paykidscompose.presentation.screens.allowance.analysis.ExpenseAnalysis
import com.paykidscompose.presentation.screens.allowance.detail.CategoryDetail
import com.paykidscompose.presentation.screens.home.Home
import com.paykidscompose.presentation.screens.login.LoginScreen
import com.paykidscompose.presentation.screens.login.nickname.NicknameScreen
import com.paykidscompose.presentation.screens.mypage.MyPageScreen
import com.paykidscompose.presentation.screens.mypage.info.MyInfoScreen
import com.paykidscompose.presentation.screens.mypage.terms.TermsPolicyScreen
import com.paykidscompose.presentation.screens.quiz.QuizClearScreen
import com.paykidscompose.presentation.screens.quiz.QuizEntryScreen
import com.paykidscompose.presentation.screens.quiz.QuizScreen
import com.paykidscompose.presentation.screens.quiz.QuizViewModel
import com.paykidscompose.presentation.screens.study.Study
import com.paykidscompose.presentation.ui.components.AppBottomBar


@Composable
fun PayKidsApp(
) {
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    // 다형성이 지원이 안되는 듯
//    val currentRoute =  navBackStackEntry?.toRoute<NavigationRoute>()
    val currentRoute = navBackStackEntry?.destination?.route

    Log.d("PayKidsApp", "CurrentRoute: $currentRoute")

    var isLogin by remember { mutableStateOf(false) }

    val quizViewModel = remember { QuizViewModel() }

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
                LoginScreen {
                    navController.navigate(EntryNavigationRoute.LoginNicknameRoute)
                }
            }

            composable<EntryNavigationRoute.LoginNicknameRoute> {
                NicknameScreen {
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

                QuizEntryScreen(
                    stageNumber = stageNumber,
                    quizViewModel = quizViewModel,
                    onQuiz = {
                        navController.navigate(QuizNavigationRoute.QuizRoute(stageNumber))
                    },
                    onStudyClick = {
                        navController.navigate(QuizNavigationRoute.StudyRoute)
                    }
                )
            }

            composable<QuizNavigationRoute.QuizRoute> { backStack ->
                val targetRoute = backStack.toRoute<QuizNavigationRoute.QuizRoute>()
                val stageNumber = targetRoute.stageNumber

                QuizScreen(
                    stageNumber = stageNumber,
                    quizViewModel = quizViewModel,
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

                QuizClearScreen(
                    clearType = clearType,
                    onExitClick = {
                        navController.navigate(TabNavigationRoute.HomeRoute)
                    }
                )
            }

            composable<QuizNavigationRoute.StudyRoute> {
                Study(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
            composable<TabNavigationRoute.MyPageRoute> {
                MyPageScreen(
                    onClickMyInfo = {
                        navController.navigate(MyPageNavigationRoute.MyInfoRoute)
                    },
                    onClickTerms = {
                        navController.navigate(MyPageNavigationRoute.TermsPolicyRoute)
                    })
            }

            composable<MyPageNavigationRoute.MyInfoRoute> {
                MyInfoScreen(
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
                AllowanceDiaryScreen(
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