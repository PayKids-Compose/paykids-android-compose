package com.paykidscompose.presentation.util

import java.time.format.DateTimeFormatter
import java.util.Locale


val MonthFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("M월", Locale.KOREAN)
val DateFormatterDay: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
val DateFormatterMonth: DateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy-MM")

fun formatAmount(amount: Int): String {
    return "%,d".format(kotlin.math.abs(amount))
}