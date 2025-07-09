package com.paykidscompose.data.database

import android.content.Context
import android.content.SharedPreferences

object PayKidsPreference {
    private const val PREFERENCE_NAME = "PayKidsPreference"
    private var INSTANCE: SharedPreferences? = null

    fun init(context: Context) {
        if (INSTANCE == null) {
            INSTANCE = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        }
    }

    fun getInstance(): SharedPreferences {
        return INSTANCE!!
    }
}