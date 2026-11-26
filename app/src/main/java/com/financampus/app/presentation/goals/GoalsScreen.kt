package com.financampus.app.presentation.goals

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.financampus.app.domain.model.FinancialGoal
import com.financampus.app.presentation.components.BalanceCard
import com.financampus.app.presentation.components.money
import java.time.LocalDate
import kotlin.math.roundToInt

@Composable
fun GoalsScreen(vm: GoalsViewModel) {

    var form by remember { mutableStateOf(false) }

    var editing by remember {
        mutableStateOf<FinancialGoal?>(null)
    }

    var selected by remember {
        mutableStateOf<FinancialGoal?>(null)
    }

    var deleting by remember {
        mutableStateOf<FinancialGoal?>(null)
    }

    var withdraw by remember { mutableStateOf(false) }
    var contribution by remember { mutableStateOf("") }

    val closeForm: () -> Unit = {
        form = false
        editing = null
        vm.clearError()
    }

    BackHandler(form) {
        if (!vm.busy) closeForm()
    }

    if (vm.busy) {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth()
        )
    }

    if (vm.error.isNotBlank()) {
        Text(
            vm.error,
            color = MaterialTheme.colorScheme.error
        )

        if (!form && selected == null && deleting == null) {
            TextButton(
                onClick = { vm.refresh() },
                enabled = !vm.busy
            ) {
                Text("Atualizar dados")
            }
        }
    }

    if (form) {
        GoalFormScreen(vm, editing, closeForm)
    } else {

        Text(
            "Minhas metas",
            style = MaterialTheme.typography.headlineMedium
        )

        BalanceCard(
            "Total reservado",
            vm.goals.sumOf { it.savedInCents }
        )

        Text(
            "${vm.goals.count { it.completed }} de " +
                "${vm.goals.size} metas conclu\u00eddas"
        )

        Button(
            onClick = {
                vm.clearError()
                editing = null
                form = true
            },
            enabled = !vm.busy,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Criar nova meta")
        }

        if (vm.goals.isEmpty() && !vm.busy) {
            Text("Nenhuma meta cadastrada.")
        }

        vm.goals.forEach { goal ->
            key(goal.id) {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement =
                            Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            goal.title,
                            style = MaterialTheme.typography.titleLarge
                        )

                        if (goal.description.isNotBlank()) {
                            Text(goal.description)
                        }

                        Text(
                            "${money(goal.savedInCents)} de " +
                                money(goal.targetInCents)
                        )

                        LinearProgressIndicator(
                            progress = { goal.progress },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text(
                            "${(goal.progress * 100).roundToInt()}%"
                        )

                        Text("Prazo: ${goal.deadline}")

                        when {
                            goal.completed ->
                                Text(
                                    "Meta conclu\u00edda!",
                                    color = MaterialTheme.colorScheme.primary
                                )

                            goal.deadline < LocalDate.now() ->
                                Text(
                                    "Prazo encerrado",
                                    color = MaterialTheme.colorScheme.error
                                )
                        }

                        Text(
                            "Faltam ${money(goal.remainingInCents)}"
                        )

                        Row(
                            horizontalArrangement =
                                Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    vm.clearError()
                                    selected = goal
                                    contribution = ""
                                    withdraw = false
                                },
                                enabled = !vm.busy &&
                                    !goal.completed,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Adicionar")
                            }

                            OutlinedButton(
                                onClick = {
                                    vm.clearError()
                                    selected = goal
                                    contribution = ""
                                    withdraw = true
                                },
                                enabled = !vm.busy &&
                                    goal.savedInCents > 0L,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Retirar")
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(
                                onClick = {
                                    vm.clearError()
                                    editing = goal
                                    form = true
                                },
                                enabled = !vm.busy
                            ) {
                                Text("Editar")
                            }

                            TextButton(
                                onClick = {
                                    vm.clearError()
                                    deleting = goal
                                },
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
            }
        }
    }

    selected?.let { goal ->
        AlertDialog(
            onDismissRequest = {
                if (!vm.busy) selected = null
            },
            title = {
                Text(
                    if (withdraw) "Retirar valor"
                    else "Adicionar valor"
                )
            },
            text = {
                Column {
                    Text(goal.title)

                    OutlinedTextField(
                        contribution,
                        { contribution = it },
                        label = { Text("Valor (R$)") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        enabled = !vm.busy
                    )

                    if (vm.error.isNotBlank()) {
                        Text(
                            vm.error,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    enabled = !vm.busy,
                    onClick = {
                        vm.changeContribution(
                            goal,
                            contribution,
                            withdraw
                        ) {
                            selected = null
                        }
                    }
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(
                    enabled = !vm.busy,
                    onClick = {
                        selected = null
                        vm.clearError()
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    deleting?.let { goal ->
        AlertDialog(
            onDismissRequest = {
                if (!vm.busy) deleting = null
            },
            title = { Text("Excluir meta?") },
            text = {
                Column {
                    Text(
                        "Deseja excluir ${goal.title}? " +
                            "Essa operacao nao pode ser desfeita."
                    )

                    if (vm.error.isNotBlank()) {
                        Text(
                            vm.error,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    enabled = !vm.busy,
                    onClick = {
                        vm.delete(goal.id) {
                            deleting = null
                        }
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
                    enabled = !vm.busy,
                    onClick = {
                        deleting = null
                        vm.clearError()
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}
