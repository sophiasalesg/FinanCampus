package com.financampus.app.presentation.components
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
@Composable
fun FinanTextField(label: String, value: String, onChange: (String) -> Unit, password: Boolean = false, email: Boolean = false) {
    var visible by remember { mutableStateOf(false) }
    OutlinedTextField(value = value, onValueChange = onChange, label = { Text(label) },
        modifier = Modifier.fillMaxWidth(), singleLine = true, shape = RoundedCornerShape(12.dp),
        visualTransformation = if (password && !visible) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = if (password) { { TextButton(onClick = { visible = !visible }) { Text(if (visible) "Ocultar" else "Mostrar") } } } else null,
        keyboardOptions = KeyboardOptions(keyboardType = when { password -> KeyboardType.Password; email -> KeyboardType.Email; else -> KeyboardType.Text }),
        colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = MaterialTheme.colorScheme.surface, unfocusedContainerColor = MaterialTheme.colorScheme.surface))
}
