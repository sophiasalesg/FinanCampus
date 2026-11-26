package com.financampus.app.domain.model

import java.time.LocalDate

data class Transaction(val id: String, val userId: String, val type: TransactionType,
    val amountInCents: Long, val category: String, val description: String, val date: LocalDate) {
    init { require(amountInCents > 0); require(category.isNotBlank()) }
    fun isIncome() = type == TransactionType.INCOME
    fun isExpense() = type == TransactionType.EXPENSE
}
