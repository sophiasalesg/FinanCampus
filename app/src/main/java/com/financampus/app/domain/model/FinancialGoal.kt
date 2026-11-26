package com.financampus.app.domain.model

import java.time.LocalDate

data class FinancialGoal(
    val id: String,
    val userId: String,
    val title: String,
    val description: String,
    val targetInCents: Long,
    val savedInCents: Long,
    val deadline: LocalDate,
    val createdAt: LocalDate
) {
    init {
        require(title.isNotBlank())
        require(title.length <= 100)
        require(description.length <= 500)
        require(targetInCents in 1L..100000000000L)
        require(savedInCents in 0L..targetInCents)
    }

    val progress: Float
        get() = (savedInCents.toDouble() /
            targetInCents).toFloat()

    val remainingInCents: Long
        get() = targetInCents - savedInCents

    val completed: Boolean
        get() = savedInCents >= targetInCents
}