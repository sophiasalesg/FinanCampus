package com.financampus.app.domain.usecase.goal

import com.financampus.app.domain.repository.GoalRepository

class GetGoalsUseCase(private val repository: GoalRepository) {
    suspend operator fun invoke() = repository.getAll()
}