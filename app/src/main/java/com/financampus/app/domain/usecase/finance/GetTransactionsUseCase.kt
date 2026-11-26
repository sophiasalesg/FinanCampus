package com.financampus.app.domain.usecase.finance

import com.financampus.app.domain.repository.TransactionRepository
class GetTransactionsUseCase(private val repository: TransactionRepository) {
    suspend operator fun invoke() = repository.getAll()
}
