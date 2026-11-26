package com.financampus.app.presentation.auth.register
import androidx.compose.runtime.*
import com.financampus.app.presentation.auth.AuthViewModel
import com.financampus.app.domain.usecase.auth.RegisterUseCase
class RegisterViewModel(private val useCase: RegisterUseCase): AuthViewModel() {
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirm by mutableStateOf("")
    fun submit() = run(when { name.isBlank() -> "Informe seu nome."; !validEmail(email) -> "Informe um e-mail válido."; password.length < 6 -> "Use pelo menos seis caracteres."; password != confirm -> "As senhas não coincidem."; else -> null }) { useCase(name.trim(), email.trim(), password); Unit }
}
