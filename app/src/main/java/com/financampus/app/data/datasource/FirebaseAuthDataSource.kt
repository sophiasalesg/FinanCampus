package com.financampus.app.data.datasource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
class FirebaseAuthDataSource(private val auth: FirebaseAuth) {
    suspend fun login(email: String, password: String) = requireNotNull(auth.signInWithEmailAndPassword(email, password).await().user)
    suspend fun register(name: String, email: String, password: String): com.google.firebase.auth.FirebaseUser {
        val user = requireNotNull(auth.createUserWithEmailAndPassword(email, password).await().user)
        try { user.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build()).await() }
        catch (e: Exception) { auth.signOut(); throw e }
        return user
    }
    suspend fun reset(email: String) { auth.sendPasswordResetEmail(email).await() }
    fun current() = auth.currentUser
    fun logout() = auth.signOut()
}
