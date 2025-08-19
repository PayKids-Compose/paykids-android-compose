package com.paykidscompose.data.di

import com.paykidscompose.data.network.service.AchievementApiService
import com.paykidscompose.data.network.service.AuthApiService
import com.paykidscompose.data.network.service.ChatApiService
import com.paykidscompose.data.network.service.ExpenseAllowanceApiService
import com.paykidscompose.data.network.service.ExpenseCategoryApiService
import com.paykidscompose.data.network.service.IncomeAllowanceApiService
import com.paykidscompose.data.network.service.IncomeCategoryApiService
import com.paykidscompose.data.network.service.QuestApiService
import com.paykidscompose.data.network.service.QuizApiService
import com.paykidscompose.data.network.service.UserApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun provideAchievementApiService(retrofit: Retrofit): AchievementApiService =
        retrofit.create(AchievementApiService::class.java)

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService =
        retrofit.create(AuthApiService::class.java)

    @Provides
    @Singleton
    fun provideExpenseApiService(retrofit: Retrofit): ExpenseAllowanceApiService =
        retrofit.create(ExpenseAllowanceApiService::class.java)

    @Provides
    @Singleton
    fun provideExpenseCategoryApiService(retrofit: Retrofit): ExpenseCategoryApiService =
        retrofit.create(ExpenseCategoryApiService::class.java)

    @Provides
    @Singleton
    fun provideIncomeApiService(retrofit: Retrofit): IncomeAllowanceApiService =
        retrofit.create(IncomeAllowanceApiService::class.java)

    @Provides
    @Singleton
    fun provideIncomeCategoryApiService(retrofit: Retrofit): IncomeCategoryApiService =
        retrofit.create(IncomeCategoryApiService::class.java)

    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit): UserApiService =
        retrofit.create(UserApiService::class.java)

    @Provides
    @Singleton
    fun provideQuizApiService(retrofit: Retrofit): QuizApiService =
        retrofit.create(QuizApiService::class.java)

    @Provides
    @Singleton
    fun provideQuestApiService(retrofit: Retrofit): QuestApiService =
        retrofit.create(QuestApiService::class.java)

    @Provides
    @Singleton
    fun provideChatApiService(retrofit: Retrofit): ChatApiService =
        retrofit.create(ChatApiService::class.java)
}