package com.financampus.app.presentation.auth.login
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.financampus.app.presentation.components.*
@Composable fun LoginScreen(vm: LoginViewModel, onSuccess: () -> Unit, onRecovery: () -> Unit, onRegister: () -> Unit) {
    LaunchedEffect(vm.success) { if (vm.success) onSuccess() }
    AuthLayout("Bem-vindo de volta", "Entre para ver suas finanças") {
        FinanTextField("E-mail", vm.email, { vm.email = it }, email = true)
        FinanTextField("Senha", vm.password, { vm.password = it }, password = true)
        FinanButton(if (vm.busy) "Aguarde..." else "Entrar", enabled = !vm.busy) { vm.submit() }
        if (vm.message.isNotEmpty()) Text(vm.message, color = MaterialTheme.colorScheme.onSurface)
        TextButton(onClick = onRecovery, enabled = !vm.busy, modifier = Modifier.fillMaxWidth()) { Text("Esqueci minha senha") }
        TextButton(onClick = onRegister, enabled = !vm.busy, modifier = Modifier.fillMaxWidth()) { Text("Ainda não tem conta? Criar conta") }
    }
}
