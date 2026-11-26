package com.financampus.app.presentation.history

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.financampus.app.domain.model.Transaction
import com.financampus.app.presentation.home.FinanceViewModel
import com.financampus.app.presentation.components.TransactionRow

@Composable
fun HistoryScreen(
    vm: FinanceViewModel,
    onEdit: (Transaction) -> Unit
) {
    var filter by remember { mutableStateOf(0) }
    var selected by remember {
        mutableStateOf<Transaction?>(null)
    }

    Text(
        "Movimenta\u00e7\u00f5es",
        style = MaterialTheme.typography.headlineMedium
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        listOf("Todas", "Receitas", "Despesas")
            .forEachIndexed { i, label ->
                FilterChip(
                    selected = filter == i,
                    onClick = { filter = i },
                    label = { Text(label) }
                )
            }
    }

    val items = vm.items.filter {
        filter == 0 ||
        (filter == 1 && it.isIncome()) ||
        (filter == 2 && it.isExpense())
    }.sortedByDescending { it.date }

    if (items.isEmpty()) {
        Text("Nenhuma movimenta\u00e7\u00e3o encontrada.")
    }

    items.forEach { item ->
        key(item.id) {
            TransactionRow(item)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = { onEdit(item) },
                    enabled = !vm.busy
                ) {
                    Text("Editar")
                }

                TextButton(
                    onClick = { selected = item },
                    enabled = !vm.busy
                ) {
                    Text(
                        "Excluir",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }

    selected?.let { item ->
        AlertDialog(
            onDismissRequest = { selected = null },
            title = {
                Text("Excluir movimenta\u00e7\u00e3o?")
            },
            text = {
                Text(
                    "Deseja excluir ${item.description.ifBlank { item.category }}? Essa opera\u00e7\u00e3o n\u00e3o poder\u00e1 ser desfeita."
                )
            },
            confirmButton = {
                TextButton(
                    enabled = !vm.busy,
                    onClick = {
                        selected = null
                        vm.delete(item.id)
                    }
                ) {
                    Text(
                        "Excluir",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { selected = null }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}