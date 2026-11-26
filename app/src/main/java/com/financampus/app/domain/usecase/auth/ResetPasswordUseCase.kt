package com.financampus.app.domain.usecase.auth

import com.financampus.app.domain.repository.AuthRepository

class ResetPasswordUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String) = repository.resetPassword(email)
}
