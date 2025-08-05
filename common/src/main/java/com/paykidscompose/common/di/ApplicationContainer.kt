package com.paykidscompose.common.di

import com.paykidscompose.common.usecase.achievement.GetAchievementsUseCase
import com.paykidscompose.common.usecase.allowance.expense.DeleteExpenseCategoryUseCase
import com.paykidscompose.common.usecase.allowance.expense.DeleteExpenseUseCase
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
import com.paykidscompose.common.usecase.quiz.GetQuizUseCase
import com.paykidscompose.common.usecase.quiz.GetStageCountUseCase
import com.paykidscompose.common.usecase.quiz.GetStageNameUseCase
import com.paykidscompose.common.usecase.quiz.GetStageToGoUseCase
import com.paykidscompose.common.usecase.quiz.GetWrongAnswerQuizzesUseCase
import com.paykidscompose.common.usecase.quiz.GetWrongAnswerStatusUseCase
import com.paykidscompose.common.usecase.user.DeleteUserUseCase
import com.paykidscompose.common.usecase.user.GetUserUseCase
import com.paykidscompose.common.usecase.user.ReplaceNicknameUseCase
import com.paykidscompose.common.usecase.user.SaveNicknameUseCase

interface ApplicationContainer {
    val loginUseCase: LoginUseCase
    val saveNicknameUseCase: SaveNicknameUseCase
    val replaceNicknameUseCase: ReplaceNicknameUseCase
    val getUserUseCase: GetUserUseCase
    val logoutUseCase: LogoutUseCase
    val deleteUserUseCase: DeleteUserUseCase
    val getStageCountUseCase: GetStageCountUseCase
    val getStageToGoUseCase: GetStageToGoUseCase
    val getStageNameUseCase: GetStageNameUseCase
    val getQuizUseCase: GetQuizUseCase
    val getAllQuizzesUseCase: GetAllQuizzesUseCase
    val getWrongAnswerQuizzesUseCase: GetWrongAnswerQuizzesUseCase
    val getWrongAnswerStatusUseCase: GetWrongAnswerStatusUseCase
    val getCheckAnswerUseCase: GetCheckAnswerUseCase
    val getCheckStageUseCase: GetCheckStageUseCase
    val getAchievementsUseCase: GetAchievementsUseCase
    val getQuestsUseCase: GetQuestsUseCase
    val getExpenseMonthTotalAmountUseCase: GetExpenseMonthTotalAmountUseCase
    val getIncomeMonthTotalAmountUseCase: GetIncomeMonthTotalAmountUseCase
    val getExpenseMonthAllCategoryUseCase: GetExpenseMonthAllCategoryUseCase
    val getIncomeMonthAllCategoryUseCase: GetIncomeMonthAllCategoryUseCase
    val getExpenseMonthMostCategoryUseCase: GetExpenseMonthMostCategoryUseCase
    val getExpenseMonthDailyAmountUseCase: GetExpenseMonthDailyAmountUseCase
    val getIncomeMonthDailyAmountUseCase: GetIncomeMonthDailyAmountUseCase
    val getExpenseDayUseCase: GetExpenseDayUseCase
    val getIncomeDayUseCase: GetIncomeDayUseCase
    val getExpenseCategoryListUseCase: GetExpenseCategoryListUseCase
    val getIncomeCategoryListUseCase: GetIncomeCategoryListUseCase
    val getExpenseMonthCategoryUseCase: GetExpenseMonthCategoryUseCase
    val getIncomeMonthCategoryUseCase: GetIncomeMonthCategoryUseCase
    val saveExpenseUseCase: SaveExpenseUseCase
    val saveIncomeUseCase: SaveIncomeUseCase
    val saveExpenseCategoryUseCase: SaveExpenseCategoryUseCase
    val saveIncomeCategoryUseCase: SaveIncomeCategoryUseCase
    val replaceExpenseUseCase: ReplaceExpenseUseCase
    val replaceIncomeUseCase: ReplaceIncomeUseCase
    val deleteExpenseCategoryUseCase: DeleteExpenseCategoryUseCase
    val deleteIncomeCategoryUseCase: DeleteIncomeCategoryUseCase
    val deleteExpenseUseCase: DeleteExpenseUseCase
    val deleteIncomeUseCase: DeleteIncomeUseCase
}