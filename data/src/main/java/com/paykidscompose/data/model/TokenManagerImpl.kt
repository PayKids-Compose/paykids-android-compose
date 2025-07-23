package com.paykidscompose.data.model

import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import com.paykidscompose.common.model.TokenManager
import com.paykidscompose.data.database.PayKidsPreference
import com.paykidscompose.data.util.ACCESS_TOKEN
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class TokenManagerImpl : TokenManager() {

    override fun getToken(): Flow<String> = callbackFlow {
        var listener: OnSharedPreferenceChangeListener? =
            OnSharedPreferenceChangeListener { sharedPreferences, key ->
                if (key == ACCESS_TOKEN) {
                    PayKidsPreference.getInstance().getString(ACCESS_TOKEN, null)
                        .let {
                            trySend(it ?: "")
                        }
                }
            }
        PayKidsPreference.getInstance().registerOnSharedPreferenceChangeListener(
            listener
        )
        awaitClose {
            PayKidsPreference.getInstance().unregisterOnSharedPreferenceChangeListener(listener)
            listener = null
        }
    }
}
