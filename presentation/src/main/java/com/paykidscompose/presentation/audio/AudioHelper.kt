package com.paykidscompose.presentation.audio

import android.content.Context

fun Context.rawUri(resId: Int): String =
    "android.resource://${packageName}/$resId"