package com.example.sportsapi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sportsapi.views.HomeView
import com.example.sportsapi.views.TeamDetailScreen

@Composable
fun NavManager() {
    val navController = rememberNavController()
    // El ViewModel se inyecta en HomeView, y lo inyectaremos en DetailScreen también

    NavHost(
        navController = navController,
        startDestination = "Home"
    ) {

        // RUTA 1: PANTALLA DE INICIO (HOME)
        composable(route = "Home") {
            HomeView(navController = navController)
        }

        // RUTA 2: PANTALLA DE DETALLE DEL EQUIPO (Nueva ruta)
        composable(
            route = "TeamDetail/{teamId}", // Usamos un nombre claro para la ruta de detalle
            arguments = listOf(
                navArgument("teamId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val teamId = backStackEntry.arguments?.getString("teamId") ?: ""
            TeamDetailScreen(teamId, navController) // Llamamos a la nueva pantalla de detalle
        }
    }
}