package com.financampus.app.domain.repository

import com.financampus.app.domain.model.FinancialGoal

interface GoalRepository {
    suspend fun add(goal: FinancialGoal)
    suspend fun getAll(): List<FinancialGoal>
    suspend fun update(goal: FinancialGoal)
    suspend fun delete(id: String)
    suspend fun contribute(id: String, cents: Long)
}