package com.financampus.app
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import com.financampus.app.presentation.theme.FinanCampusTheme
import com.financampus.app.core.navigation.AppNavGraph
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { FinanCampusTheme {
            Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) { AppNavGraph() }
        } }
    }
}
