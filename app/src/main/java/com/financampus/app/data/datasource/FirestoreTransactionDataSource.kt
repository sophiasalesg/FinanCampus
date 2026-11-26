package com.financampus.app.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.financampus.app.domain.model.Transaction
import com.financampus.app.domain.model.TransactionType
import kotlinx.coroutines.tasks.await
import java.time.LocalDate

class FirestoreTransactionDataSource(
    private val firestore: FirebaseFirestore,
    private val userId: String
) {
    private val collection
        get() = firestore.collection("users")
            .document(userId)
            .collection("transactions")

    suspend fun add(transaction: Transaction) {
        require(transaction.userId == userId)

        val data = mapOf(
            "userId" to transaction.userId,
            "type" to transaction.type.name,
            "amountInCents" to transaction.amountInCents,
            "category" to transaction.category,
            "description" to transaction.description,
            "date" to transaction.date.toString()
        )

        collection.document(transaction.id)
            .set(data)
            .await()
    }

    suspend fun getAll(): List<Transaction> {
        val snapshot = collection.get(Source.SERVER).await()

        return snapshot.documents.map { doc ->
            Transaction(
                id = doc.id,
                userId = requireNotNull(doc.getString("userId")),
                type = TransactionType.valueOf(
                    requireNotNull(doc.getString("type"))
                ),
                amountInCents = requireNotNull(
                    doc.getLong("amountInCents")
                ),
                category = requireNotNull(doc.getString("category")),
                description = doc.getString("description").orEmpty(),
                date = LocalDate.parse(
                    requireNotNull(doc.getString("date"))
                )
            )
        }.sortedByDescending { it.date }
    }

    suspend fun update(transaction: Transaction) {
        require(transaction.userId == userId)

        val data = mapOf(
            "userId" to transaction.userId,
            "type" to transaction.type.name,
            "amountInCents" to transaction.amountInCents,
            "category" to transaction.category,
            "description" to transaction.description,
            "date" to transaction.date.toString()
        )

        collection.document(transaction.id)
            .update(data)
            .await()
    }

    suspend fun delete(id: String) {
        collection.document(id).delete().await()
    }
}