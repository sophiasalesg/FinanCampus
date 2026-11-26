package com.financampus.app.presentation.theme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
private val Light = lightColorScheme(primary=Color(0xFF103D32), onPrimary=Color.White, primaryContainer=Color(0xFFB9E8CF), onPrimaryContainer=Color(0xFF103D32), secondary=Color(0xFF146B45), secondaryContainer=Color(0xFFD9F1E4), onSecondaryContainer=Color(0xFF103D32), background=Color(0xFFF5F7F6), surface=Color.White, onSurface=Color(0xFF182B25), onBackground=Color(0xFF182B25), onSurfaceVariant=Color(0xFF52665D), surfaceVariant=Color(0xFFE3EBE6), outline=Color(0xFF788A80), error=Color(0xFFB52E35), errorContainer=Color(0xFFFBE4E4), onErrorContainer=Color(0xFF801D24))
private val Dark = darkColorScheme(primary=Color(0xFF9ADDB8), onPrimary=Color(0xFF103D32), primaryContainer=Color(0xFF194D3B), onPrimaryContainer=Color(0xFFD9F1E4), secondary=Color(0xFF9ADDB8), secondaryContainer=Color(0xFF254D3C), onSecondaryContainer=Color(0xFFD9F1E4), background=Color(0xFF101B17), surface=Color(0xFF192822), onSurface=Color(0xFFE4EFE8), onBackground=Color(0xFFE4EFE8), onSurfaceVariant=Color(0xFFB5C9BE), surfaceVariant=Color(0xFF30443A), outline=Color(0xFF82998C), error=Color(0xFFFFB4AB), errorContainer=Color(0xFF632A29), onErrorContainer=Color(0xFFFFDAD6))
@Composable fun FinanCampusTheme(content: @Composable () -> Unit) {
 MaterialTheme(colorScheme=if(isSystemInDarkTheme()) Dark else Light, typography=Typography, shapes=Shapes(small=RoundedCornerShape(12.dp), medium=RoundedCornerShape(16.dp), large=RoundedCornerShape(24.dp)), content=content)
}
