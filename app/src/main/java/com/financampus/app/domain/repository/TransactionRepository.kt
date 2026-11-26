package com.financampus.app.domain.repository

import com.financampus.app.domain.model.Transaction

interface TransactionRepository {
    suspend fun add(transaction: Transaction)
    suspend fun getAll(): List<Transaction>
    suspend fun update(transaction: Transaction)
    suspend fun delete(id: String)
}
