package com.paykidscompose.presentation.util

import java.time.format.DateTimeFormatter
import java.util.Locale


val monthFormatter = DateTimeFormatter.ofPattern("M월", Locale.KOREAN)

fun formatAmount(amount: Int): String {
    return "%,d".format(kotlin.math.abs(amount))
}