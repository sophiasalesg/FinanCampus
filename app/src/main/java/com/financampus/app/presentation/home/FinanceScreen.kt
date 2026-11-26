package com.financampus.app.presentation.home

import androidx.activity.compose.BackHandler
import com.financampus.app.presentation.components.NavGlyph
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.financampus.app.domain.model.Transaction
import com.financampus.app.domain.model.TransactionType
import com.financampus.app.presentation.history.HistoryScreen
import com.financampus.app.presentation.goals.GoalsScreen
import com.financampus.app.presentation.goals.GoalsViewModel
import com.financampus.app.presentation.summary.SummaryScreen
import com.financampus.app.presentation.transaction.TransactionScreen

@Composable
fun FinanceScreen(
    vm: FinanceViewModel,
    goalsVm: GoalsViewModel,
    onLogout: () -> Unit
) {
    var tab by remember { mutableStateOf(0) }
    var entry by remember {
        mutableStateOf<TransactionType?>(null)
    }
    var editing by remember {
        mutableStateOf<Transaction?>(null)
    }

    val closeForm: () -> Unit = {
        entry = null
        editing = null
        vm.clearError()
    }

    BackHandler(entry != null) {
        if (!vm.busy) closeForm()
    }

    Scaffold(
        bottomBar = {
            if (entry == null) {
                NavigationBar {
                    listOf(
                        "In\u00edcio",
                        "Histórico",
                        "Resumo", "Metas"
                    ).forEachIndexed { i, label ->
                        NavigationBarItem(
                            selected = tab == i,
                            onClick = { tab = i },
                            enabled = !vm.busy && !goalsVm.busy,
                            icon = {
                                NavGlyph(i)
                            },
                            label = { Text(label) }
                        )
                    }
                }
            }
        }
    ) { padding ->

        Column(
            Modifier.fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (vm.busy) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (entry == null && vm.error.isNotBlank()) {
                Text(
                    vm.error,
                    color = MaterialTheme.colorScheme.error
                )

                TextButton(
                    onClick = { vm.refresh() },
                    enabled = !vm.busy && !goalsVm.busy
                ) {
                    Text("Atualizar dados")
                }
            }

            if (entry != null) {
                key(editing?.id, entry) {
                    TransactionScreen(
                        vm,
                        entry!!,
                        closeForm,
                        editing
                    )
                }
            } else {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("FinanCampus", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
                    TextButton(
                        onClick = onLogout,
                        enabled = !vm.busy && !goalsVm.busy
                    ) {
                        Text("Sair")
                    }
                }

                when (tab) {
                    0 -> HomeScreen(vm) {
                        vm.clearError()
                        editing = null
                        entry = it
                    }

                    1 -> HistoryScreen(vm) {
                        vm.clearError()
                        editing = it
                        entry = it.type
                    }

                    2 -> SummaryScreen(vm)
                    else -> GoalsScreen(goalsVm)
                }
            }
        }
    }
}
