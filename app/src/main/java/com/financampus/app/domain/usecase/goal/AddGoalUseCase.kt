package com.financampus.app.domain.usecase.goal

import com.financampus.app.domain.model.FinancialGoal
import com.financampus.app.domain.repository.GoalRepository

class AddGoalUseCase(private val repository: GoalRepository) {
    suspend operator fun invoke(goal: FinancialGoal) =
        repository.add(goal)
}