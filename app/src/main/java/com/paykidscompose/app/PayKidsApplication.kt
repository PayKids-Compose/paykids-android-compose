package com.paykidscompose.app

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.paykidscompose.app.di.ApplicationContainerImpl
import com.paykidscompose.common.di.ApplicationContainer
import com.paykidscompose.common.di.ApplicationContainerProvider
import com.paykidscompose.data.database.PayKidsPreference

class PayKidsApplication : Application(), ApplicationContainerProvider {
    private lateinit var appContainer: ApplicationContainer

    override fun onCreate() {
        super.onCreate()
        PayKidsPreference.init(this)
        appContainer = ApplicationContainerImpl(this)

        KakaoSdk.init(this, BuildConfig.KAKAO_KEY)
        //Log.e("Kakao", KakaoSdk.keyHash)
    }

    override fun provideAppContainer(): ApplicationContainer {
        return appContainer
    }

}