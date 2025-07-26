package com.paykidscompose.data.model

import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import com.paykidscompose.common.model.AuthStatusManager
import com.paykidscompose.data.database.PayKidsPreference
import com.paykidscompose.data.util.ACCESS_TOKEN
import com.paykidscompose.data.util.USER_REGISTERED
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

object AuthStatusManagerImpl : AuthStatusManager() {
//    private val sharedFlow = MutableSharedFlow<String>(replay = 1)
//    init {
//        var listener: OnSharedPreferenceChangeListener? =
//            OnSharedPreferenceChangeListener { sharedPreferences, key ->
//                if (key == null) {
//                    Log.e("AuthStatusManagerImpl", "Key is null")
//                    return@OnSharedPreferenceChangeListener
//                }
//                sharedFlow.tryEmit(key)
//            }
//        PayKidsPreference.getInstance().registerOnSharedPreferenceChangeListener(
//            listener
//        )
//    }

    override fun getIsRegistered(): Flow<Boolean> = callbackFlow {
        var listener: OnSharedPreferenceChangeListener? =
            OnSharedPreferenceChangeListener { sharedPreferences, key ->
                if (key == USER_REGISTERED) {
                    PayKidsPreference.getInstance().getBoolean(USER_REGISTERED, false)
                        .let {
                            trySend(it)
                        }
                }
            }

        PayKidsPreference.getInstance().getBoolean(USER_REGISTERED, false)
            .let {
                send(it)
            }

        PayKidsPreference.getInstance().registerOnSharedPreferenceChangeListener(
            listener
        )
        awaitClose {
            PayKidsPreference.getInstance().unregisterOnSharedPreferenceChangeListener(listener)
            listener = null
        }
//        PayKidsPreference.getInstance().getBoolean(USER_REGISTERED, false)
//            .let {
//                send(it)
//            }
//        val job = sharedFlow.onEach { key ->
//            if (key == USER_REGISTERED) {
//                PayKidsPreference.getInstance().getBoolean(USER_REGISTERED, false)
//                    .let {
//                        send(it)
//                    }
//            }
//        }
//            .launchIn(this)
//        awaitClose {
//            job.cancel()
//        }
    }

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
        PayKidsPreference.getInstance().getString(ACCESS_TOKEN, null)
            .let {
                send(it ?: "")
            }
        PayKidsPreference.getInstance().registerOnSharedPreferenceChangeListener(
            listener
        )
        awaitClose {
            PayKidsPreference.getInstance().unregisterOnSharedPreferenceChangeListener(listener)
            listener = null
        }

        //        PayKidsPreference.getInstance().getString(ACCESS_TOKEN, null)
//            .let {
//                trySendBlocking(it ?: "")
//            }
//        val job = sharedFlow.onEach { key ->
//            if (key == ACCESS_TOKEN) {
//                PayKidsPreference.getInstance().getString(ACCESS_TOKEN, null)
//                    .let {
//                        send(it ?: "")
//                    }
//            }
//        }
//            .launchIn(this)
//        awaitClose {
//            job.cancel()
//        }
    }
}
