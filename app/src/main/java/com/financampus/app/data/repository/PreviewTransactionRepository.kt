package com.financampus.app.data.repository
import com.financampus.app.domain.model.Transaction
import com.financampus.app.domain.repository.TransactionRepository
class PreviewTransactionRepository(private val userId: String): TransactionRepository {
    private val items = mutableListOf<Transaction>()
    override suspend fun add(transaction: Transaction) { require(transaction.userId == userId); items.add(transaction) }
    override suspend fun getAll() = items.toList()
    override suspend fun update(transaction: Transaction) {
        require(transaction.userId == userId)
        val index = items.indexOfFirst { it.id == transaction.id }
        require(index >= 0)
        items[index] = transaction
    }

    override suspend fun delete(id: String) { items.removeAll { it.id == id } }
}
