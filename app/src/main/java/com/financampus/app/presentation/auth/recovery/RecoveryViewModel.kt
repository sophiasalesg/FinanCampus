package com.financampus.app.presentation.auth.recovery
import androidx.compose.runtime.*
import com.financampus.app.presentation.auth.AuthViewModel
import com.financampus.app.domain.usecase.auth.ResetPasswordUseCase
class RecoveryViewModel(private val useCase: ResetPasswordUseCase): AuthViewModel() {
    var email by mutableStateOf("")
    fun submit() = run(if (!validEmail(email)) "Informe um e-mail válido." else null) { useCase(email.trim()); Unit }
}
