package com.financampus.app.domain.usecase.goal

import com.financampus.app.domain.repository.GoalRepository

class ContributeGoalUseCase(private val repository: GoalRepository) {
    suspend operator fun invoke(id: String, cents: Long) =
        repository.contribute(id, cents)
}