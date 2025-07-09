package com.paykidscompose.data.network

import com.paykidscompose.data.BuildConfig
import com.paykidscompose.data.network.service.AchievementApiService
import com.paykidscompose.data.network.service.AuthApiService
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

    private val achievementApiService = retrofit.create(AchievementApiService::class.java)
    private val authApiService = retrofit.create(AuthApiService::class.java)

    fun provideAchievementApiService(): AchievementApiService {
        return achievementApiService
    }

    fun provideAuthApiService(): AuthApiService {
        return authApiService
    }
}