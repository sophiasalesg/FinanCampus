package com.financampus.app.presentation.auth.recovery
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.financampus.app.presentation.components.*
@Composable fun RecoveryScreen(vm: RecoveryViewModel, onBack: () -> Unit) {
    AuthLayout("Esqueceu sua senha?", "Informe o e-mail cadastrado.") {
        FinanTextField("E-mail", vm.email, { vm.email = it }, email = true)
        FinanButton(if (vm.busy) "Aguarde..." else "Enviar link de recuperação", enabled = !vm.busy) { vm.submit() }
        if (vm.message.isNotEmpty()) Text(vm.message, color = MaterialTheme.colorScheme.onSurface)
        if (vm.success) Text("Se houver uma conta associada, verifique seu e-mail.")
        TextButton(onClick = onBack, enabled = !vm.busy, modifier = Modifier.fillMaxWidth()) { Text("Voltar para entrar") }
    }
}
