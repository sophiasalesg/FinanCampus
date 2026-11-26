package com.financampus.app.presentation.goals

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.financampus.app.domain.model.FinancialGoal
import java.math.BigDecimal
import java.time.LocalDate

@Composable
fun GoalFormScreen(
    vm: GoalsViewModel,
    editing: FinancialGoal?,
    onClose: () -> Unit
) {
    var title by remember {
        mutableStateOf(editing?.title.orEmpty())
    }

    var description by remember {
        mutableStateOf(editing?.description.orEmpty())
    }

    var target by remember {
        mutableStateOf(
            editing?.let {
                BigDecimal.valueOf(it.targetInCents, 2)
                    .toPlainString()
                    .replace('.', ',')
            }.orEmpty()
        )
    }

    var deadline by remember {
        mutableStateOf(
            editing?.deadline?.toString()
                ?: LocalDate.now().plusMonths(3).toString()
        )
    }

    Text(
        if (editing == null) "Nova meta" else "Editar meta",
        style = MaterialTheme.typography.headlineMedium
    )

    OutlinedTextField(
        title,
        { title = it },
        label = { Text("Nome da meta") },
        enabled = !vm.busy,
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
        description,
        { description = it },
        label = { Text("Descri\u00e7\u00e3o") },
        enabled = !vm.busy,
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
        target,
        { target = it },
        label = { Text("Valor desejado (R$)") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        enabled = !vm.busy,
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
        deadline,
        { deadline = it },
        label = { Text("Prazo (AAAA-MM-DD)") },
        enabled = !vm.busy,
        modifier = Modifier.fillMaxWidth()
    )

    Button(
        onClick = {
            vm.save(
                title, description, target,
                deadline, editing, onClose
            )
        },
        enabled = !vm.busy,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Salvar meta")
    }

    TextButton(
        onClick = onClose,
        enabled = !vm.busy
    ) {
        Text("Cancelar")
    }
}
