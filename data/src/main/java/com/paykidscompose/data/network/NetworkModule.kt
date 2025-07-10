package com.paykidscompose.data.network

import com.paykidscompose.data.BuildConfig
import com.paykidscompose.data.network.service.AchievementApiService
import com.paykidscompose.data.network.service.AuthApiService
import com.paykidscompose.data.network.service.ExpenseAllowanceApiService
import com.paykidscompose.data.network.service.ExpenseCategoryApiService
import com.paykidscompose.data.network.service.IncomeAllowanceApiService
import com.paykidscompose.data.network.service.IncomeCategoryApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkModule {
    private val client = OkHttpClient.Builder()
        .addInterceptor(NetworkInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build()

    private val achievementApiService by lazy { retrofit.create(AchievementApiService::class.java) }
    private val authApiService by lazy { retrofit.create(AuthApiService::class.java) }
    private val expenseApiService by lazy { retrofit.create(ExpenseAllowanceApiService::class.java) }
    private val expenseCategoryApiService by lazy { retrofit.create(ExpenseCategoryApiService::class.java) }
    private val incomeApiService by lazy { retrofit.create(IncomeAllowanceApiService::class.java) }
    private val incomeCategoryApiService by lazy { retrofit.create(IncomeCategoryApiService::class.java) }

    fun provideAchievementApiService(): AchievementApiService = achievementApiService

    fun provideAuthApiService(): AuthApiService = authApiService

    fun provideExpenseApiService(): ExpenseAllowanceApiService = expenseApiService

    fun provideExpenseCategoryApiService(): ExpenseCategoryApiService = expenseCategoryApiService

    fun provideIncomeApiService(): IncomeAllowanceApiService = incomeApiService

    fun provideIncomeCategoryApiService(): IncomeCategoryApiService = incomeCategoryApiService
}