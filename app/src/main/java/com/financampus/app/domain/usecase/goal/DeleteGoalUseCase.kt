package com.financampus.app.domain.usecase.goal

import com.financampus.app.domain.repository.GoalRepository

class DeleteGoalUseCase(private val repository: GoalRepository) {
    suspend operator fun invoke(id: String) =
        repository.delete(id)
}