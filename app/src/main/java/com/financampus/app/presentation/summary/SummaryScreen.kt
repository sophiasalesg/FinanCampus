package com.financampus.app.presentation.summary
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.financampus.app.presentation.home.FinanceViewModel
import com.financampus.app.presentation.components.*
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale
@Composable fun SummaryScreen(vm: FinanceViewModel) {
    var month by remember { mutableStateOf(YearMonth.now()) }
    val items = vm.items.filter { YearMonth.from(it.date) == month }
    val summary = vm.summary(items)
    Text("Resumo do mês", style = MaterialTheme.typography.headlineMedium)
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { TextButton(onClick = { month = month.minusMonths(1) }) { Text("‹") }; Text(month.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale("pt", "BR")))); TextButton(onClick = { month = month.plusMonths(1) }) { Text("›") } }
    Row(horizontalArrangement=Arrangement.spacedBy(12.dp)) { MetricCard("Receitas", summary.totalIncome, false, Modifier.weight(1f)); MetricCard("Despesas", summary.totalExpenses, true, Modifier.weight(1f)) }
    Text("Gastos por categoria", style = MaterialTheme.typography.titleLarge)
    if(summary.totalExpenses == 0L) Text("Sem despesas neste mês.")
    items.filter { it.isExpense() }.groupBy { it.category }.mapValues { (_, list) -> list.sumOf { it.amountInCents } }.entries.sortedByDescending { it.value }.forEach { (category, amount) ->
        Text("$category — ${money(amount)}")
        LinearProgressIndicator(progress = { (amount.toDouble() / summary.totalExpenses).toFloat() }, modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.error)
    }
    BalanceCard("Resultado do mês", summary.balance)
}
