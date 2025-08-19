package com.paykidscompose.data.di

import com.paykidscompose.common.model.AuthStatusManager
import com.paykidscompose.common.repository.AchievementRepository
import com.paykidscompose.common.repository.AuthRepository
import com.paykidscompose.common.repository.ChatRepository
import com.paykidscompose.common.repository.ExpenseAllowanceRepository
import com.paykidscompose.common.repository.ExpenseCategoryRepository
import com.paykidscompose.common.repository.IncomeAllowanceRepository
import com.paykidscompose.common.repository.IncomeCategoryRepository
import com.paykidscompose.common.repository.QuestRepository
import com.paykidscompose.common.repository.QuizRepository
import com.paykidscompose.common.repository.UserRepository
import com.paykidscompose.data.model.AuthStatusManagerImpl
import com.paykidscompose.data.repository.AchievementRepositoryImpl
import com.paykidscompose.data.repository.AuthRepositoryImpl
import com.paykidscompose.data.repository.ChatRepositoryImpl
import com.paykidscompose.data.repository.ExpenseAllowanceRepositoryImpl
import com.paykidscompose.data.repository.ExpenseCategoryRepositoryImpl
import com.paykidscompose.data.repository.IncomeAllowanceRepositoryImpl
import com.paykidscompose.data.repository.IncomeCategoryRepositoryImpl
import com.paykidscompose.data.repository.QuestRepositoryImpl
import com.paykidscompose.data.repository.QuizRepositoryImpl
import com.paykidscompose.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun provideExpenseAllowanceRepository(
        expenseAllowanceRepositoryImpl: ExpenseAllowanceRepositoryImpl
    ): ExpenseAllowanceRepository

    @Binds
    @Singleton
    abstract fun provideExpenseCategoryRepository(
        expenseCategoryRepositoryImpl: ExpenseCategoryRepositoryImpl
    ): ExpenseCategoryRepository

    @Binds
    @Singleton
    abstract fun provideIncomeAllowanceRepository(
        incomeAllowanceRepositoryImpl: IncomeAllowanceRepositoryImpl
    ): IncomeAllowanceRepository

    @Binds
    @Singleton
    abstract fun provideIncomeCategoryRepository(
        incomeCategoryRepositoryImpl: IncomeCategoryRepositoryImpl
    ): IncomeCategoryRepository

    @Binds
    @Singleton
    abstract fun provideUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun provideAchievementRepository(
        achievementRepositoryImpl: AchievementRepositoryImpl
    ): AchievementRepository

    @Binds
    @Singleton
    abstract fun provideChatRepository(
        chatRepositoryImpl: ChatRepositoryImpl
    ): ChatRepository

    @Binds
    @Singleton
    abstract fun provideQuestRepository(
        questRepositoryImpl: QuestRepositoryImpl
    ): QuestRepository

    @Binds
    @Singleton
    abstract fun provideQuizRepository(
        quizRepositoryImpl: QuizRepositoryImpl
    ): QuizRepository

    @Binds
    @Singleton
    abstract fun provideAuthStatusManager(
        authStatusManagerImpl: AuthStatusManagerImpl
    ) : AuthStatusManager
}