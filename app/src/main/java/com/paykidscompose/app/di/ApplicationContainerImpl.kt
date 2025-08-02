package com.paykidscompose.app.di

import android.content.Context
import com.paykidscompose.common.di.ApplicationContainer
import com.paykidscompose.common.usecase.allowance.expense.GetExpenseDayUseCase
import com.paykidscompose.common.usecase.allowance.expense.GetExpenseMonthDailyAmountUseCase
import com.paykidscompose.common.usecase.allowance.expense.GetExpenseMonthMostCategoryUseCase
import com.paykidscompose.common.usecase.allowance.expense.GetExpenseMonthTotalAmountUseCase
import com.paykidscompose.common.usecase.allowance.expense.ReplaceExpenseUseCase
import com.paykidscompose.common.usecase.allowance.expense.SaveExpenseUseCase
import com.paykidscompose.common.usecase.allowance.income.GetIncomeDayUseCase
import com.paykidscompose.common.usecase.allowance.income.GetIncomeMonthDailyAmountUseCase
import com.paykidscompose.common.usecase.allowance.income.ReplaceIncomeUseCase
import com.paykidscompose.common.usecase.allowance.income.SaveIncomeUseCase
import com.paykidscompose.common.usecase.authentication.LoginUseCase
import com.paykidscompose.common.usecase.authentication.LogoutUseCase
import com.paykidscompose.common.usecase.quiz.GetAllQuizzesUseCase
import com.paykidscompose.common.usecase.quiz.GetCheckAnswerUseCase
import com.paykidscompose.common.usecase.quiz.GetCheckStageUseCase
import com.paykidscompose.common.usecase.quiz.GetQuizUseCase
import com.paykidscompose.common.usecase.quiz.GetStageCountUseCase
import com.paykidscompose.common.usecase.quiz.GetStageNameUseCase
import com.paykidscompose.common.usecase.quiz.GetStageToGoUseCase
import com.paykidscompose.common.usecase.quiz.GetWrongAnswerQuizzesUseCase
import com.paykidscompose.common.usecase.user.DeleteUserUseCase
import com.paykidscompose.common.usecase.user.GetUserUseCase
import com.paykidscompose.common.usecase.user.ReplaceNicknameUseCase
import com.paykidscompose.common.usecase.user.SaveNicknameUseCase
import com.paykidscompose.data.network.NetworkModule
import com.paykidscompose.data.network.service.authentication.KakaoLoginService
import com.paykidscompose.data.repositories.AuthRepositoryImpl
import com.paykidscompose.data.repositories.ExpenseAllowanceRepositoryImpl
import com.paykidscompose.data.repositories.IncomeAllowanceRepositoryImpl
import com.paykidscompose.data.repositories.QuizRepositoryImpl
import com.paykidscompose.data.repositories.UserRepositoryImpl

class ApplicationContainerImpl(
    context: Context
) : ApplicationContainer {

    private val authRepository = AuthRepositoryImpl(
        NetworkModule.provideAuthApiService(),
        KakaoLoginService(context)
    )

    private val userRepository = UserRepositoryImpl(
        NetworkModule.provideUserApiService()
    )

    private val quizRepository = QuizRepositoryImpl(
        NetworkModule.provideQuizApiService()
    )

    private val expenseAllowanceRepository = ExpenseAllowanceRepositoryImpl(
        NetworkModule.provideExpenseApiService()
    )

    private val incomeAllowanceRepository = IncomeAllowanceRepositoryImpl(
        NetworkModule.provideIncomeApiService()
    )

    override val loginUseCase: LoginUseCase = LoginUseCase(authRepository)
    override val saveNicknameUseCase: SaveNicknameUseCase = SaveNicknameUseCase(userRepository)
    override val getUserUseCase: GetUserUseCase = GetUserUseCase(userRepository)
    override val logoutUseCase: LogoutUseCase = LogoutUseCase(authRepository)
    override val deleteUserUseCase: DeleteUserUseCase = DeleteUserUseCase(userRepository)
    override val replaceNicknameUseCase: ReplaceNicknameUseCase = ReplaceNicknameUseCase(userRepository)

    override val getStageCountUseCase: GetStageCountUseCase = GetStageCountUseCase(quizRepository)
    override val getStageToGoUseCase: GetStageToGoUseCase = GetStageToGoUseCase(quizRepository)
    override val getStageNameUseCase: GetStageNameUseCase = GetStageNameUseCase(quizRepository)

    override val getQuizUseCase: GetQuizUseCase = GetQuizUseCase(quizRepository)
    override val getAllQuizzesUseCase: GetAllQuizzesUseCase = GetAllQuizzesUseCase(quizRepository)
    override val getWrongAnswerQuizzesUseCase: GetWrongAnswerQuizzesUseCase = GetWrongAnswerQuizzesUseCase(quizRepository)
    override val getCheckAnswerUseCase: GetCheckAnswerUseCase = GetCheckAnswerUseCase(quizRepository)
    override val getCheckStageUseCase: GetCheckStageUseCase = GetCheckStageUseCase(quizRepository)

    override val getExpenseMonthTotalAmountUseCase: GetExpenseMonthTotalAmountUseCase =
        GetExpenseMonthTotalAmountUseCase(expenseAllowanceRepository)
    override val getExpenseMonthMostCategoryUseCase: GetExpenseMonthMostCategoryUseCase =
        GetExpenseMonthMostCategoryUseCase(expenseAllowanceRepository)
    override val getExpenseMonthDailyAmountUseCase: GetExpenseMonthDailyAmountUseCase =
        GetExpenseMonthDailyAmountUseCase(expenseAllowanceRepository)
    override val getIncomeMonthDailyAmountUseCase: GetIncomeMonthDailyAmountUseCase =
        GetIncomeMonthDailyAmountUseCase(incomeAllowanceRepository)
    override val getExpenseDayUseCase: GetExpenseDayUseCase = GetExpenseDayUseCase(expenseAllowanceRepository)
    override val getIncomeDayUseCase: GetIncomeDayUseCase = GetIncomeDayUseCase(incomeAllowanceRepository)
    override val saveExpenseUseCase: SaveExpenseUseCase = SaveExpenseUseCase(expenseAllowanceRepository)
    override val saveIncomeUseCase: SaveIncomeUseCase = SaveIncomeUseCase(incomeAllowanceRepository)
    override val replaceExpenseUseCase: ReplaceExpenseUseCase =
        ReplaceExpenseUseCase(expenseAllowanceRepository)
    override val replaceIncomeUseCase: ReplaceIncomeUseCase = ReplaceIncomeUseCase(incomeAllowanceRepository)
}