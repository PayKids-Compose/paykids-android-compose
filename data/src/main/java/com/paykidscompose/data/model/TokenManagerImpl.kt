package com.paykidscompose.data.model

import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.util.Log
import com.paykidscompose.common.model.TokenManager
import com.paykidscompose.data.database.PayKidsPreference
import com.paykidscompose.data.util.ACCESS_TOKEN
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

object TokenManagerImpl : TokenManager() {
    private val sharedFlow = MutableSharedFlow<String>(replay = 1)
    init {
        var listener: OnSharedPreferenceChangeListener? =
            OnSharedPreferenceChangeListener { sharedPreferences, key ->
                if (key == null) {
                    Log.e("TokenManagerImpl", "Key is null")
                    return@OnSharedPreferenceChangeListener
                }
                sharedFlow.tryEmit(key)
            }
        PayKidsPreference.getInstance().registerOnSharedPreferenceChangeListener(
            listener
        )
    }

    override fun getIsRegistered(): Flow<Boolean> = callbackFlow {
        PayKidsPreference.getInstance().getBoolean("isRegistered", false)
            .let {
                send(it)
            }
        val job = sharedFlow.onEach { key ->
            if (key == "isRegistered") {
                PayKidsPreference.getInstance().getBoolean("isRegistered", false)
                    .let {
                        send(it)
                    }
            }
        }
            .launchIn(this)
        awaitClose {
            job.cancel()
        }
    }

    override fun getToken(): Flow<String> = callbackFlow {
        PayKidsPreference.getInstance().getString(ACCESS_TOKEN, null)
            .let {
                send(it ?: "")
            }
        val job = sharedFlow.onEach { key ->
            if (key == ACCESS_TOKEN) {
                PayKidsPreference.getInstance().getString(ACCESS_TOKEN, null)
                    .let {
                        send(it ?: "")
                    }
            }
        }
            .launchIn(this)
        awaitClose {
            job.cancel()
        }
    }
}
