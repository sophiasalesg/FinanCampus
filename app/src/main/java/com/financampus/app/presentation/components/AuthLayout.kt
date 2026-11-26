package com.financampus.app.presentation.components
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
@Composable fun AuthLayout(title: String, subtitle: String, content: @Composable ColumnScope.() -> Unit) {
    Column(Modifier.fillMaxSize().safeDrawingPadding().imePadding().verticalScroll(rememberScrollState()).padding(horizontal = 28.dp, vertical = 32.dp), verticalArrangement = Arrangement.Center) {
        Surface(color=MaterialTheme.colorScheme.primaryContainer, shape=MaterialTheme.shapes.large) { Text("FC", modifier=Modifier.padding(18.dp), style=MaterialTheme.typography.headlineMedium, color=MaterialTheme.colorScheme.onPrimaryContainer) }
        Spacer(Modifier.height(12.dp))
        Text("FinanCampus", style=MaterialTheme.typography.titleLarge, color=MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(24.dp))
        Text(title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text(subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(32.dp))
        Column(verticalArrangement = Arrangement.spacedBy(12.dp), content = content)
    }
}
