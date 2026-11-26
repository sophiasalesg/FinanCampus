package com.financampus.app.data.datasource

import com.financampus.app.domain.model.FinancialGoal
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await
import java.time.LocalDate

class FirestoreGoalDataSource(
    private val firestore: FirebaseFirestore,
    private val userId: String
) {
    private val collection
        get() = firestore.collection("users")
            .document(userId)
            .collection("goals")

    private fun data(goal: FinancialGoal): Map<String, Any> =
        mapOf(
            "userId" to goal.userId,
            "title" to goal.title,
            "description" to goal.description,
            "targetInCents" to goal.targetInCents,
            "savedInCents" to goal.savedInCents,
            "deadline" to goal.deadline.toString(),
            "createdAt" to goal.createdAt.toString()
        )

    suspend fun add(goal: FinancialGoal) {
        require(goal.userId == userId)

        collection.document(goal.id)
            .set(data(goal))
            .await()
    }

    suspend fun getAll(): List<FinancialGoal> {
        return collection.get(Source.SERVER)
            .await()
            .documents.map { doc ->
                FinancialGoal(
                    id = doc.id,
                    userId = requireNotNull(
                        doc.getString("userId")
                    ),
                    title = requireNotNull(
                        doc.getString("title")
                    ),
                    description = doc.getString(
                        "description"
                    ).orEmpty(),
                    targetInCents = requireNotNull(
                        doc.getLong("targetInCents")
                    ),
                    savedInCents = requireNotNull(
                        doc.getLong("savedInCents")
                    ),
                    deadline = LocalDate.parse(
                        requireNotNull(
                            doc.getString("deadline")
                        )
                    ),
                    createdAt = LocalDate.parse(
                        requireNotNull(
                            doc.getString("createdAt")
                        )
                    )
                )
            }.sortedBy { it.deadline }
    }

    suspend fun update(goal: FinancialGoal) {
        require(goal.userId == userId)

        val ref = collection.document(goal.id)

        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(ref)

            val saved = requireNotNull(
                snapshot.getLong("savedInCents")
            )

            require(goal.targetInCents >= saved)

            transaction.update(
                ref,
                mapOf(
                    "title" to goal.title,
                    "description" to goal.description,
                    "targetInCents" to goal.targetInCents,
                    "deadline" to goal.deadline.toString()
                )
            )

            Unit
        }.await()
    }

    suspend fun delete(id: String) {
        collection.document(id).delete().await()
    }

    suspend fun contribute(id: String, cents: Long) {
        require(cents != 0L)
        require(cents in -100000000000L..100000000000L)

        val ref = collection.document(id)

        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(ref)

            val saved = requireNotNull(
                snapshot.getLong("savedInCents")
            )

            val target = requireNotNull(
                snapshot.getLong("targetInCents")
            )

            val updated = saved + cents

            require(updated in 0L..target)

            transaction.update(
                ref,
                "savedInCents",
                updated
            )

            Unit
        }.await()
    }
}