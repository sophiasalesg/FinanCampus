package com.financampus.app.data.repository

import com.financampus.app.data.datasource.FirestoreGoalDataSource
import com.financampus.app.domain.model.FinancialGoal
import com.financampus.app.domain.repository.GoalRepository

class GoalRepositoryImpl(
    private val source: FirestoreGoalDataSource
) : GoalRepository {

    override suspend fun add(goal: FinancialGoal) =
        source.add(goal)

    override suspend fun getAll() =
        source.getAll()

    override suspend fun update(goal: FinancialGoal) =
        source.update(goal)

    override suspend fun delete(id: String) =
        source.delete(id)

    override suspend fun contribute(
        id: String,
        cents: Long
    ) {
        source.contribute(id, cents)
    }
}