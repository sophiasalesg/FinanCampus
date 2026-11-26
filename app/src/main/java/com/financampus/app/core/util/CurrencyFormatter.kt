package com.financampus.app.core.util

import java.text.NumberFormat
import java.util.Locale
import java.math.BigDecimal
object CurrencyFormatter {
    fun format(cents: Long): String = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR")).format(BigDecimal.valueOf(cents, 2))
}
