package com.financampus.app.presentation.components
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.financampus.app.domain.model.Transaction
import java.text.NumberFormat
import java.math.BigDecimal
import java.util.Locale
import java.time.format.DateTimeFormatter
fun money(cents: Long): String = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(BigDecimal.valueOf(cents, 2))
@Composable fun TransactionRow(item: Transaction) {
 val c=MaterialTheme.colorScheme
 Row(Modifier.fillMaxWidth().padding(vertical=12.dp), verticalAlignment=Alignment.CenterVertically, horizontalArrangement=Arrangement.spacedBy(12.dp)) {
 Surface(shape=MaterialTheme.shapes.medium, color=if(item.isIncome()) c.primaryContainer else c.errorContainer) { Box(Modifier.size(44.dp), contentAlignment=Alignment.Center) { Text(if(item.isIncome()) "↙" else "↗", style=MaterialTheme.typography.titleLarge, color=if(item.isIncome()) c.onPrimaryContainer else c.onErrorContainer) } }
 Column(Modifier.weight(1f)) { Text(item.description.ifBlank { item.category }, style=MaterialTheme.typography.titleMedium); Text("${item.category} · ${item.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}", style=MaterialTheme.typography.bodySmall, color=c.onSurfaceVariant) }
 Text((if(item.isIncome()) "+ " else "− ")+money(item.amountInCents), color=if(item.isIncome()) c.primary else c.error, style=MaterialTheme.typography.labelLarge)
 }
 HorizontalDivider(color=c.surfaceVariant)
}
@Composable fun BalanceCard(label:String, amount:Long) {
 Card(colors=CardDefaults.cardColors(containerColor=MaterialTheme.colorScheme.primaryContainer), modifier=Modifier.fillMaxWidth(), shape=MaterialTheme.shapes.large) {
 Column(Modifier.padding(24.dp), verticalArrangement=Arrangement.spacedBy(8.dp)) { Text(label, color=MaterialTheme.colorScheme.onPrimaryContainer); Text(money(amount), style=MaterialTheme.typography.headlineMedium, color=MaterialTheme.colorScheme.onPrimaryContainer) }
 }
}
@Composable fun MetricCard(label:String, amount:Long, expense:Boolean, modifier:Modifier=Modifier) {
 Card(modifier=modifier, colors=CardDefaults.cardColors(containerColor=MaterialTheme.colorScheme.surface)) { Column(Modifier.padding(14.dp), verticalArrangement=Arrangement.spacedBy(6.dp)) { Text(if(expense) "↗" else "↙", color=if(expense) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary); Text(label, style=MaterialTheme.typography.bodySmall); Text(money(amount), style=MaterialTheme.typography.titleMedium, color=if(expense) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary) } }
}
