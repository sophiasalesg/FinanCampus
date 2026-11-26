package com.financampus.app.domain.usecase.goal

import com.financampus.app.domain.model.FinancialGoal
import com.financampus.app.domain.repository.GoalRepository

class UpdateGoalUseCase(private val repository: GoalRepository) {
    suspend operator fun invoke(goal: FinancialGoal) =
        repository.update(goal)
}