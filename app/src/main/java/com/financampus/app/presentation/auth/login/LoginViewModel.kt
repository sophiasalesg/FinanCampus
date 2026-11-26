package com.financampus.app.presentation.auth.login
import androidx.compose.runtime.*
import com.financampus.app.presentation.auth.AuthViewModel
import com.financampus.app.domain.usecase.auth.LoginUseCase
class LoginViewModel(private val useCase: LoginUseCase): AuthViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    fun submit() = run(when { !validEmail(email) -> "Informe um e-mail válido."; password.isBlank() -> "Informe sua senha."; else -> null }) { useCase(email.trim(), password); Unit }
}
