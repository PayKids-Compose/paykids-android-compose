package com.paykidscompose.presentation.util

fun formatAmount(amount: Int): String {
    return "%,d".format(kotlin.math.abs(amount))
}