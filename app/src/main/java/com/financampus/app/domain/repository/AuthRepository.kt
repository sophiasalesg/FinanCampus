package com.financampus.app.domain.repository

import com.financampus.app.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): User
    suspend fun register(name: String, email: String, password: String): User
    suspend fun resetPassword(email: String)
    fun currentUser(): User?
    fun logout()
}
