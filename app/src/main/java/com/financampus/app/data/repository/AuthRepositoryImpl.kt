package com.financampus.app.data.repository
import com.financampus.app.data.datasource.FirebaseAuthDataSource
import com.financampus.app.domain.repository.AuthRepository
import com.financampus.app.domain.model.User
import com.google.firebase.auth.FirebaseUser
class AuthRepositoryImpl(private val source: FirebaseAuthDataSource): AuthRepository {
    private fun FirebaseUser.toDomain() = User(uid, displayName.orEmpty(), email.orEmpty())
    override suspend fun login(email: String, password: String) = source.login(email,password).toDomain()
    override suspend fun register(name: String,email: String,password: String) = source.register(name,email,password).toDomain()
    override suspend fun resetPassword(email: String) = source.reset(email)
    override fun currentUser() = source.current()?.toDomain()
    override fun logout() = source.logout()
}
