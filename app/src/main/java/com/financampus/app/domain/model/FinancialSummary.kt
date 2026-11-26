package com.financampus.app.domain.model

data class FinancialSummary(val totalIncome: Long, val totalExpenses: Long) {
    val balance: Long get() = totalIncome - totalExpenses
}
