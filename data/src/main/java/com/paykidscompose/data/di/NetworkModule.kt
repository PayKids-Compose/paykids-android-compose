package com.paykidscompose.data.di

import com.paykidscompose.data.BuildConfig
import com.paykidscompose.data.network.NetworkInterceptor
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
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkModule {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(NetworkInterceptor())
        .build()
    private val moshi = MoshiConverterFactory.create(
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    )
    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(moshi)
        .client(client)
        .build()

    private val achievementApiService by lazy { retrofit.create(AchievementApiService::class.java) }
    private val authApiService by lazy { retrofit.create(AuthApiService::class.java) }
    private val expenseApiService by lazy { retrofit.create(ExpenseAllowanceApiService::class.java) }
    private val expenseCategoryApiService by lazy { retrofit.create(ExpenseCategoryApiService::class.java) }
    private val incomeApiService by lazy { retrofit.create(IncomeAllowanceApiService::class.java) }
    private val incomeCategoryApiService by lazy { retrofit.create(IncomeCategoryApiService::class.java) }
    private val userApiService by lazy { retrofit.create(UserApiService::class.java) }
    private val quizApiService by lazy { retrofit.create(QuizApiService::class.java) }
    private val questApiService by lazy { retrofit.create(QuestApiService::class.java) }
    private val chatApiService by lazy { retrofit.create(ChatApiService::class.java) }

    fun provideAchievementApiService(): AchievementApiService = achievementApiService

    fun provideAuthApiService(): AuthApiService = authApiService

    fun provideExpenseApiService(): ExpenseAllowanceApiService = expenseApiService

    fun provideExpenseCategoryApiService(): ExpenseCategoryApiService = expenseCategoryApiService

    fun provideIncomeApiService(): IncomeAllowanceApiService = incomeApiService

    fun provideIncomeCategoryApiService(): IncomeCategoryApiService = incomeCategoryApiService

    fun provideUserApiService(): UserApiService = userApiService

    fun provideQuizApiService(): QuizApiService = quizApiService

    fun provideQuestApiService(): QuestApiService = questApiService

    fun provideChatApiService(): ChatApiService = chatApiService
}