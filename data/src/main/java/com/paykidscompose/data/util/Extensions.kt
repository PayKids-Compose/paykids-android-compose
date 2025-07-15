package com.paykidscompose.data.util

import android.content.Intent
import android.os.Bundle


fun <T> Intent.getCustomParcelableExtra(name: String, clazz: Class<T>): T? {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
        return getParcelableExtra(name, clazz)
    }
    return getParcelableExtra(name)
}

fun <T> Bundle.getCustomParcelableExtra(name: String, clazz: Class<T>): T? {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
        return getParcelable(name, clazz)
    }
    return getParcelable(name)
}