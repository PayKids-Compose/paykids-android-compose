package com.paykidscompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.paykidscompose.presentation.model.QuizClearType
import com.paykidscompose.presentation.navigation.route.AllowanceDiaryNavigationRoute
import com.paykidscompose.presentation.navigation.route.MyPageNavigationRoute
import com.paykidscompose.presentation.navigation.route.QuizNavigationRoute
import com.paykidscompose.presentation.navigation.route.TabNavigationRoute
import com.paykidscompose.presentation.navigation.route.toRoute
import com.paykidscompose.presentation.screens.allowance.AllowanceDiaryScreen
import com.paykidscompose.presentation.screens.allowance.analysis.ExpenseAnalysis
import com.paykidscompose.presentation.screens.allowance.detail.CategoryDetail
import com.paykidscompose.presentation.screens.home.HomeScreen
import com.paykidscompose.presentation.screens.mypage.MyPageScreen
import com.paykidscompose.presentation.screens.mypage.info.MyInfoScreen
import com.paykidscompose.presentation.screens.mypage.terms.TermsPolicyScreen
import com.paykidscompose.presentation.screens.quiz.QuizClearScreen
import com.paykidscompose.presentation.screens.quiz.QuizEntryScreen
import com.paykidscompose.presentation.screens.quiz.QuizScreen

@Composable
fun TabNavGraph(
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = TabNavigationRoute.HomeRoute.toRoute()
    ) {
        composable(
            TabNavigationRoute.HomeRoute.toRoute()
        ) {
            HomeScreen(navController) { stageNumber ->
                navController.navigate(QuizNavigationRoute.QuizEntryRoute(stageNumber).toRoute())
            }
        }

        composable(
            route = "quiz_entry/{stageNumber}",
            arguments = listOf(
                navArgument("stageNumber") {
                    type = NavType.IntType
                    nullable = false
                }
            )
        ) { backStack ->
            val stageNumber = requireNotNull(backStack.arguments?.getInt("stageNumber"))

            QuizEntryScreen(
                stageNumber = stageNumber,
                onQuiz = {
                    navController.navigate(QuizNavigationRoute.QuizRoute(stageNumber).toRoute())
                }
            )
        }

        composable(
            route = "quiz/{stageNumber}",
            arguments = listOf(
                navArgument("stageNumber") {
                    type = NavType.IntType
                    nullable = false
                }
            )
        ) { backStack ->
            val stageNumber = requireNotNull(backStack.arguments?.getInt("stageNumber"))

            QuizScreen(
                stageNumber = stageNumber,
                onBackClick = {
                    navController.popBackStack()
                },
                onConfirmClick = { clear ->
                    navController.navigate(QuizNavigationRoute.QuizClearRoute(clear).toRoute())
                }
            )
        }

        composable(
            route = "quiz_clear/{clear}",
            arguments = listOf(
                navArgument("clear") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { backStack ->
            val clear = requireNotNull(backStack.arguments?.getString("clear"))
            val clearType = QuizClearType.valueOf(clear)

            QuizClearScreen(
                clearType = clearType,
                onExitClick = {
                    navController.navigate(TabNavigationRoute.HomeRoute.toRoute())
                }
            )
        }

        composable(
            TabNavigationRoute.MyPageRoute.toRoute()
        ) {
            MyPageScreen(
                navController,
                onClickMyInfo = {
                    navController.navigate(MyPageNavigationRoute.MyInfoRoute.toRoute())
                },
                onClickTerms = {
                    navController.navigate(MyPageNavigationRoute.TermsPolicyRoute.toRoute())
                })
        }

        composable(
            MyPageNavigationRoute.MyInfoRoute.toRoute()
        ) {
            MyInfoScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            MyPageNavigationRoute.TermsPolicyRoute.toRoute()
        ) {
            TermsPolicyScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            TabNavigationRoute.AllowanceDiaryRoute.toRoute()
        ) {
            AllowanceDiaryScreen(
                navController,
                onCategoryExpense = {
                    navController.navigate(AllowanceDiaryNavigationRoute.ExpenseAnalysisRoute.toRoute())
                }
            )
        }

        composable(
            AllowanceDiaryNavigationRoute.ExpenseAnalysisRoute.toRoute()
        ) {
            ExpenseAnalysis(
                navController,
                onCategoryCard = {
                    navController.navigate(AllowanceDiaryNavigationRoute.CategoryDetailRoute.toRoute())
                }
            )
        }

        composable(
            AllowanceDiaryNavigationRoute.CategoryDetailRoute.toRoute()
        ) {
            CategoryDetail(
                navController
            )
        }
    }
}