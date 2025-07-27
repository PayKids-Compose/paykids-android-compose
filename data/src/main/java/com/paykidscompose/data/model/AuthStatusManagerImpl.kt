package com.paykidscompose.data.model

import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import com.paykidscompose.common.model.AuthStatusManager
import com.paykidscompose.data.database.PayKidsPreference
import com.paykidscompose.data.util.ACCESS_TOKEN
import com.paykidscompose.data.util.USER_REGISTERED
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

object AuthStatusManagerImpl : AuthStatusManager() {
    private val preference = PayKidsPreference.getInstance()
    private val registeredFlow = MutableSharedFlow<Boolean>(replay = 1)
    private val tokenFlow = MutableSharedFlow<String>(replay = 1)

    private val listener = OnSharedPreferenceChangeListener { _, key ->
        when (key) {
            USER_REGISTERED -> {
                registeredFlow.tryEmit(preference.getBoolean(USER_REGISTERED, false))
            }

            ACCESS_TOKEN -> {
                tokenFlow.tryEmit(preference.getString(ACCESS_TOKEN, null) ?: "")
            }
        }
    }

    init {
        preference.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun getIsRegistered(): Flow<Boolean> = callbackFlow {
        send(preference.getBoolean(USER_REGISTERED, false))
        val job = registeredFlow.onEach { key ->
            send(preference.getBoolean(USER_REGISTERED, false))
        }
            .launchIn(this)
        awaitClose {
            job.cancel()
        }
    }

    override fun getToken(): Flow<String> = callbackFlow {
        send(preference.getString(ACCESS_TOKEN, null) ?: "")
        val job = tokenFlow.onEach { key ->
            send(preference.getString(ACCESS_TOKEN, null) ?: "")
        }
            .launchIn(this)
        awaitClose {
            job.cancel()
        }
    }

    fun notifyLoginScreenNav() {
        registeredFlow.tryEmit(false)
        tokenFlow.tryEmit("")
    }
}
