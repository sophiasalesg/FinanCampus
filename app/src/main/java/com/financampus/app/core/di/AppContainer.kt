package com.financampus.app.core.di

import com.financampus.app.data.datasource.FirebaseAuthDataSource
import com.financampus.app.data.datasource.FirestoreTransactionDataSource
import com.financampus.app.data.repository.AuthRepositoryImpl
import com.financampus.app.data.repository.TransactionRepositoryImpl
import com.financampus.app.domain.repository.AuthRepository
import com.financampus.app.domain.repository.TransactionRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AppContainer {

    fun goals(userId: String):
        com.financampus.app.domain.repository.GoalRepository {

        return com.financampus.app.data.repository.GoalRepositoryImpl(
            com.financampus.app.data.datasource.FirestoreGoalDataSource(
                FirebaseFirestore.getInstance(),
                userId
            )
        )
    }

    val auth: AuthRepository =
        AuthRepositoryImpl(
            FirebaseAuthDataSource(FirebaseAuth.getInstance())
        )

    fun transactions(userId: String): TransactionRepository {
        return TransactionRepositoryImpl(
            FirestoreTransactionDataSource(
                FirebaseFirestore.getInstance(),
                userId
            )
        )
    }
}