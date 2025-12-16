package com.example.sportsapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.sportsapi.navigation.NavManager
import com.example.sportsapi.ui.theme.SportsApiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            SportsApiTheme() {
                // Llamamos al NavManager para manejar la navegación.
                // Importante: NavManager ya no recibe el ViewModel como parámetro.
                NavManager()
            }
        }
    }
}