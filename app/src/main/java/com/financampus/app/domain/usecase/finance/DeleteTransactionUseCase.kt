package com.financampus.app.domain.usecase.finance

import com.financampus.app.domain.repository.TransactionRepository

class DeleteTransactionUseCase(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(id: String) {
        repository.delete(id)
    }
}