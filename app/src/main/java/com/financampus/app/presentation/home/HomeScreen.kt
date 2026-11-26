package com.financampus.app.presentation.home
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.financampus.app.domain.model.TransactionType
import com.financampus.app.presentation.components.*
@Composable fun HomeScreen(vm: FinanceViewModel, onAdd: (TransactionType) -> Unit) {
    val summary = vm.summary(vm.items)
    Text("Suas finanças", style = MaterialTheme.typography.headlineMedium)
    Text("Organize seu dinheiro e planeje seu futuro.", color=MaterialTheme.colorScheme.onSurfaceVariant)
    BalanceCard("Saldo registrado", summary.balance)
    Row(horizontalArrangement=Arrangement.spacedBy(12.dp)) {
        MetricCard("Receitas", summary.totalIncome, false, Modifier.weight(1f))
        MetricCard("Despesas", summary.totalExpenses, true, Modifier.weight(1f))
    }
    FinanButton("Registrar receita") { onAdd(TransactionType.INCOME) }
    OutlinedButton(onClick = { onAdd(TransactionType.EXPENSE) }, modifier = Modifier.fillMaxWidth()) { Text("Registrar despesa") }
    Text("Últimas movimentações", style = MaterialTheme.typography.titleLarge)
    if(vm.items.isEmpty()) Text("Nenhuma movimentação registrada.")
    vm.items.sortedByDescending { it.date }.take(5).forEach { TransactionRow(it) }
}
