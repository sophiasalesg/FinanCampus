package com.financampus.app.domain.usecase.auth

import com.financampus.app.domain.repository.AuthRepository

class RegisterUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(name: String, email: String, password: String) = repository.register(name, email, password)
}
