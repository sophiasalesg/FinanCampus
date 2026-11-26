package com.financampus.app.domain.usecase.finance

import com.financampus.app.domain.model.Transaction
import com.financampus.app.domain.repository.TransactionRepository

class UpdateTransactionUseCase(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transaction: Transaction) {
        repository.update(transaction)
    }
}