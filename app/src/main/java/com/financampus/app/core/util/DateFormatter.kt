package com.financampus.app.core.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
object DateFormatter {
    fun format(date: LocalDate): String = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
}
