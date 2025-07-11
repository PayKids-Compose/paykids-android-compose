package com.paykidscompose.data.database

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object PayKidsPreference {
    private const val PREFERENCE_NAME = "PayKidsPreference"
    private var INSTANCE: SharedPreferences? = null

    fun init(context: Context) {
        if (INSTANCE == null) {
            val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
            INSTANCE = EncryptedSharedPreferences.create(
                PREFERENCE_NAME,
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }
    }

    fun getInstance(): SharedPreferences {
        return INSTANCE!!
    }
}