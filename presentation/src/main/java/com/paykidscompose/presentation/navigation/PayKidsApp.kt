package com.paykidscompose.presentation.navigation

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.paykidscompose.common.enums.EntryPoint
import com.paykidscompose.common.usecase.achievement.GetAchievementsUseCase
import com.paykidscompose.common.usecase.allowance.GetMonthDailyAmountsUseCase
import com.paykidscompose.common.usecase.allowance.GetSelectDayTransactionsUseCase
import com.paykidscompose.common.usecase.allowance.expense.DeleteExpenseCategoryUseCase
import com.paykidscompose.common.usecase.allowance.expense.DeleteExpenseUseCase
import com.paykidscompose.common.usecase.allowance.expense.GetExpenseAllCategoryForMonthUseCase
import com.paykidscompose.common.usecase.allowance.expense.GetExpenseCategoryListUseCase
import com.paykidscompose.common.usecase.allowance.expense.GetExpenseDayUseCase
import com.paykidscompose.common.usecase.allowance.expense.GetExpenseMonthAllCategoryUseCase
import com.paykidscompose.common.usecase.allowance.expense.GetExpenseMonthCategoryUseCase
import com.paykidscompose.common.usecase.allowance.expense.GetExpenseMonthDailyAmountUseCase
import com.paykidscompose.common.usecase.allowance.expense.GetExpenseMonthMostCategoryUseCase
import com.paykidscompose.common.usecase.allowance.expense.GetExpenseMonthTotalAmountUseCase
import com.paykidscompose.common.usecase.allowance.expense.ReplaceExpenseUseCase
import com.paykidscompose.common.usecase.allowance.expense.SaveExpenseCategoryUseCase
import com.paykidscompose.common.usecase.allowance.expense.SaveExpenseUseCase
import com.paykidscompose.common.usecase.allowance.income.DeleteIncomeCategoryUseCase
import com.paykidscompose.common.usecase.allowance.income.DeleteIncomeUseCase
import com.paykidscompose.common.usecase.allowance.income.GetIncomeAllCategoryForMonthUseCase
import com.paykidscompose.common.usecase.allowance.income.GetIncomeCategoryListUseCase
import com.paykidscompose.common.usecase.allowance.income.GetIncomeDayUseCase
import com.paykidscompose.common.usecase.allowance.income.GetIncomeMonthAllCategoryUseCase
import com.paykidscompose.common.usecase.allowance.income.GetIncomeMonthCategoryUseCase
import com.paykidscompose.common.usecase.allowance.income.GetIncomeMonthDailyAmountUseCase
import com.paykidscompose.common.usecase.allowance.income.GetIncomeMonthTotalAmountUseCase
import com.paykidscompose.common.usecase.allowance.income.ReplaceIncomeUseCase
import com.paykidscompose.common.usecase.allowance.income.SaveIncomeCategoryUseCase
import com.paykidscompose.common.usecase.allowance.income.SaveIncomeUseCase
import com.paykidscompose.common.usecase.authentication.LoginUseCase
import com.paykidscompose.common.usecase.authentication.LogoutUseCase
import com.paykidscompose.common.usecase.quest.GetQuestsUseCase
import com.paykidscompose.common.usecase.quiz.GetAllQuizzesUseCase
import com.paykidscompose.common.usecase.quiz.GetCheckAnswerUseCase
import com.paykidscompose.common.usecase.quiz.GetCheckStageUseCase
import com.paykidscompose.common.usecase.quiz.GetStageCountUseCase
import com.paykidscompose.common.usecase.quiz.GetStageNameUseCase
import com.paykidscompose.common.usecase.quiz.GetStageToGoUseCase
import com.paykidscompose.common.usecase.quiz.GetWrongAnswerQuizzesUseCase
import com.paykidscompose.common.usecase.quiz.GetWrongAnswerStatusUseCase
import com.paykidscompose.common.usecase.study.GetChatResponseUseCase
import com.paykidscompose.common.usecase.user.DeleteUserUseCase
import com.paykidscompose.common.usecase.user.GetUserUseCase
import com.paykidscompose.common.usecase.user.ReplaceNicknameUseCase
import com.paykidscompose.common.usecase.user.SaveNicknameUseCase
import com.paykidscompose.presentation.factory.AllowanceDiaryViewModelFactory
import com.paykidscompose.presentation.factory.CategoryDetailViewModelFactory
import com.paykidscompose.presentation.factory.HomeViewModelFactory
import com.paykidscompose.presentation.factory.LoginNicknameViewModelFactory
import com.paykidscompose.presentation.factory.LoginViewModelFactory
import com.paykidscompose.presentation.factory.MyInfoViewModelFactory
import com.paykidscompose.presentation.factory.MyPageViewModelFactory
import com.paykidscompose.presentation.factory.QuestAndAchievementViewModelFactory
import com.paykidscompose.presentation.factory.QuizEntryViewModelFactory
import com.paykidscompose.presentation.factory.QuizViewModelFactory
import com.paykidscompose.presentation.factory.StudyViewModelFactory
import com.paykidscompose.presentation.factory.TransactionAnalysisViewModelFactory
import com.paykidscompose.presentation.navigation.route.AllowanceDiaryNavigationRoute
import com.paykidscompose.presentation.navigation.route.EntryNavigationRoute
import com.paykidscompose.presentation.navigation.route.MyPageNavigationRoute
import com.paykidscompose.presentation.navigation.route.QuizNavigationRoute
import com.paykidscompose.presentation.navigation.route.TabNavigationRoute
import com.paykidscompose.presentation.screen.PayKidsScaffold
import com.paykidscompose.presentation.screen.allowance.AllowanceDiary
import com.paykidscompose.presentation.screen.allowance.AllowanceDiaryViewModel
import com.paykidscompose.presentation.screen.allowance.analysis.TransactionAnalysis
import com.paykidscompose.presentation.screen.allowance.analysis.TransactionAnalysisViewModel
import com.paykidscompose.presentation.screen.allowance.detail.CategoryDetail
import com.paykidscompose.presentation.screen.allowance.detail.CategoryDetailViewModel
import com.paykidscompose.presentation.screen.home.Home
import com.paykidscompose.presentation.screen.home.HomeViewModel
import com.paykidscompose.presentation.screen.login.Login
import com.paykidscompose.presentation.screen.login.LoginViewModel
import com.paykidscompose.presentation.screen.login.nickname.LoginNicknameViewModel
import com.paykidscompose.presentation.screen.login.nickname.Nickname
import com.paykidscompose.presentation.screen.mypage.MyPage
import com.paykidscompose.presentation.screen.mypage.MyPageViewModel
import com.paykidscompose.presentation.screen.mypage.info.MyInfo
import com.paykidscompose.presentation.screen.mypage.info.MyInfoViewModel
import com.paykidscompose.presentation.screen.mypage.terms.TermsPolicyScreen
import com.paykidscompose.presentation.screen.quest.QuestAndAchievement
import com.paykidscompose.presentation.screen.quest.QuestAndAchievementViewModel
import com.paykidscompose.presentation.screen.quiz.Quiz
import com.paykidscompose.presentation.screen.quiz.QuizViewModel
import com.paykidscompose.presentation.screen.quiz.clear.QuizClear
import com.paykidscompose.presentation.screen.quiz.entry.QuizEntry
import com.paykidscompose.presentation.screen.quiz.entry.QuizEntryViewModel
import com.paykidscompose.presentation.screen.study.Study
import com.paykidscompose.presentation.screen.study.StudyViewModel
import com.paykidscompose.presentation.ui.components.AppBottomBar


@Composable
fun PayKidsApp(
    loginStatus: EntryPoint,
    loginUseCase: LoginUseCase,
    saveNicknameUseCase: SaveNicknameUseCase,
    getUserUseCase: GetUserUseCase,
    deleteUserUseCase: DeleteUserUseCase,
    logoutUseCase: LogoutUseCase,
    replaceNicknameUseCase: ReplaceNicknameUseCase,
    getStageCountUseCase: GetStageCountUseCase,
    getStageToGoUseCase: GetStageToGoUseCase,
    getStageNameUseCase: GetStageNameUseCase,
    getAllQuizzesUseCase: GetAllQuizzesUseCase,
    getWrongAnswerUseCase: GetWrongAnswerQuizzesUseCase,
    getWrongAnswerStatusUseCase: GetWrongAnswerStatusUseCase,
    getCheckAnswerUseCase: GetCheckAnswerUseCase,
    getCheckStageUseCase: GetCheckStageUseCase,
    getAchievementsUseCase: GetAchievementsUseCase,
    getQuestsUseCase: GetQuestsUseCase,
    getChatResponseUseCase: GetChatResponseUseCase,
    getExpenseMonthTotalAmountUseCase: GetExpenseMonthTotalAmountUseCase,
    getExpenseMonthMostCategoryUseCase: GetExpenseMonthMostCategoryUseCase,
    getExpenseMonthDailyAmountUseCase: GetExpenseMonthDailyAmountUseCase,
    getIncomeMonthDailyAmountUseCase: GetIncomeMonthDailyAmountUseCase,
    getExpenseDayUseCase: GetExpenseDayUseCase,
    getIncomeDayUseCase: GetIncomeDayUseCase,
    getExpenseCategoryListUseCase: GetExpenseCategoryListUseCase,
    getIncomeCategoryListUseCase: GetIncomeCategoryListUseCase,
    getMonthDailyAmountsUseCase: GetMonthDailyAmountsUseCase,
    getSelectDayTransactionsUseCase: GetSelectDayTransactionsUseCase,
    saveExpenseUseCase: SaveExpenseUseCase,
    saveIncomeUseCase: SaveIncomeUseCase,
    replaceExpenseUseCase: ReplaceExpenseUseCase,
    replaceIncomeUseCase: ReplaceIncomeUseCase,
    getIncomeMonthTotalAmountUseCase: GetIncomeMonthTotalAmountUseCase,
    getExpenseMonthAllCategoryUseCase: GetExpenseMonthAllCategoryUseCase,
    getIncomeMonthAllCategoryUseCase: GetIncomeMonthAllCategoryUseCase,
    deleteExpenseCategoryUseCase: DeleteExpenseCategoryUseCase,
    deleteIncomeCategoryUseCase: DeleteIncomeCategoryUseCase,
    saveExpenseCategoryUseCase: SaveExpenseCategoryUseCase,
    saveIncomeCategoryUseCase: SaveIncomeCategoryUseCase,
    getExpenseMonthCategoryUseCase: GetExpenseMonthCategoryUseCase,
    getIncomeMonthCategoryUseCase: GetIncomeMonthCategoryUseCase,
    deleteExpenseUseCase: DeleteExpenseUseCase,
    deleteIncomeUseCase: DeleteIncomeUseCase,
    getExpenseAllCategoryForMonthUseCase: GetExpenseAllCategoryForMonthUseCase,
    getIncomeAllCategoryForMonthUseCase: GetIncomeAllCategoryForMonthUseCase
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
                val viewModel: LoginViewModel = viewModel(
                    viewModelStoreOwner = it,
                    factory = LoginViewModelFactory(loginUseCase)
                )
                Login(viewModel) {
                    navController.navigate(EntryNavigationRoute.LoginNicknameRoute)
                }
            }

            composable<EntryNavigationRoute.LoginNicknameRoute> {
                val viewModel: LoginNicknameViewModel = viewModel(
                    viewModelStoreOwner = it,
                    factory = LoginNicknameViewModelFactory(saveNicknameUseCase)
                )
                Nickname(viewModel) {
                }
            }

            composable<TabNavigationRoute.HomeRoute> {
                val viewModel: HomeViewModel = viewModel(
                    viewModelStoreOwner = it,
                    factory = HomeViewModelFactory(
                        getStageCountUseCase,
                        getStageToGoUseCase,
                        getStageNameUseCase
                    )
                )
                Home(viewModel) { stageNumber, stageTitle ->
                    navController.navigate(
                        QuizNavigationRoute.QuizEntryRoute(stageNumber, stageTitle)
                    )
                }
            }

            composable<QuizNavigationRoute.QuizEntryRoute> { backStack ->
                val targetRoute = backStack.toRoute<QuizNavigationRoute.QuizEntryRoute>()
                val stageNumber = targetRoute.stageNumber
                val stageTitle = targetRoute.stageTitle

                val viewModel: QuizEntryViewModel = viewModel(
                    viewModelStoreOwner = backStack,
                    factory = QuizEntryViewModelFactory(getWrongAnswerStatusUseCase)
                )

                QuizEntry(
                    stageNumber = stageNumber,
                    stageTitle = stageTitle,
                    quizEntryViewModel = viewModel,
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

                val viewModel: QuizViewModel = viewModel(
                    viewModelStoreOwner = backStack,
                    factory = QuizViewModelFactory(
                        getAllQuizzesUseCase,
                        getWrongAnswerUseCase,
                        getCheckAnswerUseCase,
                        getCheckStageUseCase
                    )
                )

                Quiz(
                    stageNumber = stageNumber,
                    isWrongAnswerNote = isWrongAnswerNote,
                    quizViewModel = viewModel,
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
//                        navController.navigate(TabNavigationRoute.HomeRoute) {
//                            popUpTo(navController.graph.findStartDestination().id)
//                            launchSingleTop = true
//                            // 백스택 맨 위에가 홈이라면 기존 화면을 재사용하기 위해서 넣음.
//                        }
                        navController.popBackStack(TabNavigationRoute.HomeRoute, inclusive = false)
                    }
                )
            }

            composable<QuizNavigationRoute.StudyRoute> { backStack ->
                val targetRoute = backStack.toRoute<QuizNavigationRoute.StudyRoute>()
                val stageNumber = targetRoute.stageNumber

                val viewModel: StudyViewModel = viewModel(
                    viewModelStoreOwner = backStack,
                    factory = StudyViewModelFactory(
                        getChatResponseUseCase,
                        getUserUseCase
                    )
                )

                Study(
                    stageNumber = stageNumber,
                    studyViewModel = viewModel,
                    onBackClick = {
                        navController.navigateUp()
                    }
                )
            }

            composable<TabNavigationRoute.QuestAndAchievementRoute> {
                val viewModel: QuestAndAchievementViewModel = viewModel(
                    viewModelStoreOwner = it,
                    factory = QuestAndAchievementViewModelFactory(
                        getAchievementsUseCase,
                        getQuestsUseCase
                    )
                )

                QuestAndAchievement(
                    questAndAchievementViewModel = viewModel
                )
            }

            composable<TabNavigationRoute.MyPageRoute> {
                val viewModel: MyPageViewModel = viewModel(
                    viewModelStoreOwner = it,
                    factory = MyPageViewModelFactory(getUserUseCase, logoutUseCase)
                )
                MyPage(
                    viewModel,
                    onClickMyInfo = {
                        navController.navigate(MyPageNavigationRoute.MyInfoRoute)
                    },
                    onClickTerms = {
                        navController.navigate(MyPageNavigationRoute.TermsPolicyRoute)
                    })
            }

            composable<MyPageNavigationRoute.MyInfoRoute> {
                val viewModel: MyInfoViewModel = viewModel(
                    viewModelStoreOwner = it,
                    factory = MyInfoViewModelFactory(
                        getUserUseCase, replaceNicknameUseCase, deleteUserUseCase
                    )
                )
                MyInfo(
                    viewModel,
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
                val viewModel: AllowanceDiaryViewModel = viewModel(
                    viewModelStoreOwner = it,
                    factory = AllowanceDiaryViewModelFactory(
                        getExpenseMonthTotalAmountUseCase,
                        getExpenseMonthMostCategoryUseCase,
                        getExpenseCategoryListUseCase,
                        getIncomeCategoryListUseCase,
                        getMonthDailyAmountsUseCase,
                        getSelectDayTransactionsUseCase,
                        saveExpenseUseCase,
                        saveIncomeUseCase,
                        replaceExpenseUseCase,
                        replaceIncomeUseCase
                    )
                )
                AllowanceDiary(
                    viewModel = viewModel,
                    onCategoryExpense = {
                        navController.navigate(AllowanceDiaryNavigationRoute.ExpenseAnalysisRoute)
                    }
                )
            }

            composable<AllowanceDiaryNavigationRoute.ExpenseAnalysisRoute> {
                val viewModel: TransactionAnalysisViewModel = viewModel(
                    viewModelStoreOwner = it,
                    factory = TransactionAnalysisViewModelFactory(
                        getExpenseMonthTotalAmountUseCase,
                        getIncomeMonthTotalAmountUseCase,
                        getExpenseAllCategoryForMonthUseCase,
                        getIncomeAllCategoryForMonthUseCase,
                        deleteExpenseCategoryUseCase,
                        deleteIncomeCategoryUseCase,
                        saveExpenseCategoryUseCase,
                        saveIncomeCategoryUseCase
                    )
                )

                TransactionAnalysis(
                    viewModel = viewModel,
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

                val viewModel: CategoryDetailViewModel = viewModel(
                    viewModelStoreOwner = it,
                    factory = CategoryDetailViewModelFactory(
                        getExpenseMonthCategoryUseCase,
                        getIncomeMonthCategoryUseCase,
                        saveExpenseUseCase,
                        saveIncomeUseCase,
                        replaceExpenseUseCase,
                        replaceIncomeUseCase,
                        deleteExpenseUseCase,
                        deleteIncomeUseCase
                    )
                )

                CategoryDetail(year, month, category, type, viewModel)
            }
        }
    }
}