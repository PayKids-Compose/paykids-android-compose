package com.paykidscompose.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paykidscompose.app.SplashActivity.Companion.LOGIN_STATUS
import com.paykidscompose.app.ui.theme.PayKidsComposeTheme
import com.paykidscompose.common.di.ApplicationContainerProvider
import com.paykidscompose.common.enums.EntryPoint
import com.paykidscompose.common.usecase.authentication.CheckUserCompletionStatusUseCase
import com.paykidscompose.data.model.AuthStatusManagerImpl
import com.paykidscompose.presentation.navigation.PayKidsApp

class MainActivity : ComponentActivity() {
    private val provider by lazy {
        (applicationContext as ApplicationContainerProvider).provideAppContainer()
    }
    private val userCompletionStatusUseCase = CheckUserCompletionStatusUseCase(AuthStatusManagerImpl)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val loginStatus by userCompletionStatusUseCase().collectAsStateWithLifecycle(
                EntryPoint.valueOf(
                    intent.getStringExtra(
                        LOGIN_STATUS
                    ) ?: EntryPoint.LOGIN.name
                )
            )

            PayKidsComposeTheme {
                PayKidsApp(
                    loginStatus,
                    provider.loginUseCase,
                    provider.saveNicknameUseCase,
                    provider.getUserUseCase,
                    provider.deleteUserUseCase,
                    provider.logoutUseCase,
                    provider.replaceNicknameUseCase,
                    provider.getStageCountUseCase,
                    provider.getStageToGoUseCase,
                    provider.getStageNameUseCase,
                    provider.getAllQuizzesUseCase,
                    provider.getWrongAnswerQuizzesUseCase,
                    provider.getWrongAnswerStatusUseCase,
                    provider.getCheckAnswerUseCase,
                    provider.getCheckStageUseCase,
                    provider.getChatResponseUseCase,
                    provider.getExpenseMonthTotalAmountUseCase,
                    provider.getExpenseMonthMostCategoryUseCase,
                    provider.getExpenseMonthDailyAmountUseCase,
                    provider.getIncomeMonthDailyAmountUseCase,
                    provider.getExpenseDayUseCase,
                    provider.getIncomeDayUseCase,
                    provider.getExpenseCategoryListUseCase,
                    provider.getIncomeCategoryListUseCase,
                    provider.saveExpenseUseCase,
                    provider.saveIncomeUseCase,
                    provider.replaceExpenseUseCase,
                    provider.replaceIncomeUseCase,
                    provider.getIncomeMonthTotalAmountUseCase,
                    provider.getExpenseMonthAllCategoryUseCase,
                    provider.getIncomeMonthAllCategoryUseCase,
                    provider.deleteExpenseCategoryUseCase,
                    provider.deleteIncomeCategoryUseCase,
                    provider.saveExpenseCategoryUseCase,
                    provider.saveIncomeCategoryUseCase
                )
            }
        }
    }
}