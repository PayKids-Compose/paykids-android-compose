package com.paykidscompose.data.network

import com.paykidscompose.data.database.PayKidsPreference
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        with(chain) {
            val newRequest = request().newBuilder()
                .addHeader("Authorization", "Bearer ${PayKidsPreference.getInstance().getString("accessToken", "")}")
                .build()
            return proceed(newRequest)
        }
    }
}