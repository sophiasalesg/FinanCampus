package com.financampus.app.presentation.transaction

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.financampus.app.domain.model.Transaction
import com.financampus.app.domain.model.TransactionType
import com.financampus.app.presentation.home.FinanceViewModel
import com.financampus.app.presentation.components.FinanButton
import java.time.LocalDate
import java.math.BigDecimal

@Composable
fun TransactionScreen(
    vm: FinanceViewModel,
    initial: TransactionType,
    onBack: () -> Unit,
    editing: Transaction? = null
) {
    var type by remember {
        mutableStateOf(editing?.type ?: initial)
    }

    var value by remember {
        mutableStateOf(
            editing?.let {
                BigDecimal.valueOf(it.amountInCents, 2)
                    .toPlainString()
                    .replace('.', ',')
            }.orEmpty()
        )
    }

    var category by remember {
        mutableStateOf(editing?.category.orEmpty())
    }

    var date by remember {
        mutableStateOf(
            editing?.date?.toString()
                ?: LocalDate.now().toString()
        )
    }

    var description by remember {
        mutableStateOf(editing?.description.orEmpty())
    }

    var expanded by remember { mutableStateOf(false) }

    val categories = if (type == TransactionType.INCOME) {
        listOf(
            "Bolsa", "Est\u00e1gio", "Sal\u00e1rio",
            "Aux\u00edlio", "Outros"
        )
    } else {
        listOf(
            "Alimenta\u00e7\u00e3o", "Transporte",
            "Educa\u00e7\u00e3o", "Moradia",
            "Sa\u00fade", "Lazer", "Outros"
        )
    }

    Text(
        if (editing == null)
            "Nova movimenta\u00e7\u00e3o"
        else
            "Editar movimenta\u00e7\u00e3o",
        style = MaterialTheme.typography.headlineMedium
    )

    Row {
        TransactionType.entries.forEach { t ->
            FilterChip(
                selected = type == t,
                enabled = !vm.busy,
                onClick = {
                    if (type != t) {
                        type = t
                        category = ""
                    }
                },
                label = {
                    Text(
                        if (t == TransactionType.INCOME)
                            "Receita"
                        else
                            "Despesa"
                    )
                }
            )
        }
    }

    OutlinedTextField(
        value,
        { value = it },
        label = { Text("Valor (R$)") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        enabled = !vm.busy,
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )

    Box {
        OutlinedButton(
            onClick = { expanded = true },
            enabled = !vm.busy,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                category.ifBlank {
                    "Selecione a categoria"
                }
            )
        }

        DropdownMenu(
            expanded,
            { expanded = false }
        ) {
            categories.forEach { c ->
                DropdownMenuItem(
                    text = { Text(c) },
                    onClick = {
                        category = c
                        expanded = false
                    }
                )
            }
        }
    }

    OutlinedTextField(
        date,
        { date = it },
        label = { Text("Data (AAAA-MM-DD)") },
        enabled = !vm.busy,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )

    OutlinedTextField(
        description,
        { description = it },
        label = { Text("Descri\u00e7\u00e3o (opcional)") },
        enabled = !vm.busy,
        modifier = Modifier.fillMaxWidth()
    )

    if (vm.error.isNotBlank()) {
        Text(
            vm.error,
            color = MaterialTheme.colorScheme.error
        )
    }

    val buttonLabel = when {
        editing != null -> "Salvar altera\u00e7\u00f5es"
        type == TransactionType.INCOME -> "Salvar receita"
        else -> "Salvar despesa"
    }

    FinanButton(buttonLabel, !vm.busy) {
        vm.save(
            type,
            value,
            category,
            date,
            description,
            onBack,
            editing?.id
        )
    }

    TextButton(
        onClick = onBack,
        enabled = !vm.busy
    ) {
        Text("Cancelar")
    }
}
