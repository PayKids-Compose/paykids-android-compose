package com.paykidscompose.common.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

val today = LocalDate.now()

val MonthFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("M월", Locale.KOREAN)
val DateFormatterDay: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
val DateFormatterMonth: DateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy-MM")
