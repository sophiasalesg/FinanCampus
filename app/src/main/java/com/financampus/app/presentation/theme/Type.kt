package com.financampus.app.presentation.theme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontWeight
private val base = Typography()
val Typography = base.copy(headlineMedium=base.headlineMedium.copy(fontWeight=FontWeight.Bold), headlineSmall=base.headlineSmall.copy(fontWeight=FontWeight.Bold), titleLarge=base.titleLarge.copy(fontWeight=FontWeight.SemiBold), titleMedium=base.titleMedium.copy(fontWeight=FontWeight.SemiBold), labelLarge=base.labelLarge.copy(fontWeight=FontWeight.SemiBold))
