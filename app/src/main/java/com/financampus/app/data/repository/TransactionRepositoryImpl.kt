package com.financampus.app.data.repository

import com.financampus.app.data.datasource.FirestoreTransactionDataSource
import com.financampus.app.domain.model.Transaction
import com.financampus.app.domain.repository.TransactionRepository

class TransactionRepositoryImpl(
    private val dataSource: FirestoreTransactionDataSource
) : TransactionRepository {

    override suspend fun add(transaction: Transaction) {
        dataSource.add(transaction)
    }

    override suspend fun getAll(): List<Transaction> {
        return dataSource.getAll()
    }

    override suspend fun update(transaction: Transaction) {
        dataSource.update(transaction)
    }

    override suspend fun delete(id: String) {
        dataSource.delete(id)
    }
}