package com.financampus.app.presentation.auth.register
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.financampus.app.presentation.components.*
@Composable fun RegisterScreen(vm: RegisterViewModel, onSuccess: () -> Unit, onBack: () -> Unit) {
    LaunchedEffect(vm.success) { if (vm.success) onSuccess() }
    AuthLayout("Crie sua conta", "Comece a organizar suas finanças") {
        FinanTextField("Nome completo", vm.name, { vm.name = it })
        FinanTextField("E-mail", vm.email, { vm.email = it }, email = true)
        FinanTextField("Senha", vm.password, { vm.password = it }, password = true)
        FinanTextField("Confirmar senha", vm.confirm, { vm.confirm = it }, password = true)
        FinanButton(if (vm.busy) "Aguarde..." else "Criar conta", enabled = !vm.busy) { vm.submit() }
        if (vm.message.isNotEmpty()) Text(vm.message, color = MaterialTheme.colorScheme.onSurface)
        TextButton(onClick = onBack, enabled = !vm.busy, modifier = Modifier.fillMaxWidth()) { Text("Já tem conta? Entrar") }
    }
}
