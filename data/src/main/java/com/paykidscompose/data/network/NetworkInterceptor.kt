package com.paykidscompose.data.network

import android.content.SharedPreferences
import com.paykidscompose.data.util.ACCESS_TOKEN
import com.paykidscompose.data.util.INTERCEPTOR_HEADER
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(
    private val preference: dagger.Lazy<SharedPreferences>
) : Interceptor {
    // 애노테이션 헤더를 사용하지 않고 인터셉터로 요청을 가로채서 헤더를 추가하기 위해 구현
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        val url = request().url.encodedPath
        if (url.contains("/auth/login") || url.contains("/auth/refresh")) {
            proceed(request())
        } else {
            val newRequest = request().newBuilder()
                .addHeader(
                    INTERCEPTOR_HEADER,
                    "Bearer ${preference.get().getString(ACCESS_TOKEN, "")}"
                )
                .build()
            proceed(newRequest)
        }
    }
}