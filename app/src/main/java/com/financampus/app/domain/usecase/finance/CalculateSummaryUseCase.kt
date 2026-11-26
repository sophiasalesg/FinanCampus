package com.financampus.app.domain.usecase.finance

import com.financampus.app.domain.model.*
class CalculateSummaryUseCase {
    operator fun invoke(transactions: List<Transaction>) = FinancialSummary(
        transactions.filter { it.isIncome() }.sumOf { it.amountInCents },
        transactions.filter { it.isExpense() }.sumOf { it.amountInCents }
    )
}
