package com.paykidscompose.app

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk

class PayKidsApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, BuildConfig.KAKAO_KEY)
        //Log.e("Kakao", KakaoSdk.keyHash)
    }
}