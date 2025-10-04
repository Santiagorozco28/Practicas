package com.example.nfl.navigation

import android.R.attr.type
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nfl.view.AFCView
import com.example.nfl.view.DallasView
import com.example.nfl.view.GreenBayView
import com.example.nfl.view.JaguarsView
import com.example.nfl.view.KansasView
import com.example.nfl.view.NFCView
import com.example.nfl.view.NFLView
import com.example.nfl.view.PatriotsView
import com.example.nfl.view.SFView
import com.example.nfl.view.SplashScreen
import com.example.nfl.view.SteelersView
import com.example.nfl.view.TampaView


@Composable
fun NavManager(){
    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = "Splash"){
        composable("NFL"){
            NFLView(navController)
        }
        composable("AFC/{id}",arguments =
            listOf(navArgument("id")
            {type= NavType.IntType })){
            val id=it.arguments?.getInt("id")?:0
            AFCView(navController,id)
        }
        composable("NFC/{id}",arguments =
            listOf(navArgument("id")
            {type= NavType.IntType })){
            val id=it.arguments?.getInt("id")?:0
            NFCView(navController,id)
        }
        //vistas de equipos
        composable("DALLAS/{id}",arguments =
            listOf(navArgument("id")
            {type= NavType.IntType })){
            val id=it.arguments?.getInt("id")?:0
            DallasView(navController,id)
        }
        composable("SF/{id}",arguments =
            listOf(navArgument("id")
            {type= NavType.IntType })){
            val id=it.arguments?.getInt("id")?:0
            SFView(navController,id)
        }
        composable("GREENBAY/{id}",arguments =
            listOf(navArgument("id")
            {type= NavType.IntType })){
            val id=it.arguments?.getInt("id")?:0
            GreenBayView(navController,id)
        }
        composable("JAGUARS/{id}",arguments =
            listOf(navArgument("id")
            {type= NavType.IntType })){
            val id=it.arguments?.getInt("id")?:0
            JaguarsView(navController,id)
        }
        composable("KANSAS/{id}",arguments =
            listOf(navArgument("id")
            {type= NavType.IntType })){
            val id=it.arguments?.getInt("id")?:0
            KansasView(navController,id)
        }
        composable("Patriots/{id}",arguments =
            listOf(navArgument("id")
            {type= NavType.IntType })){
            val id=it.arguments?.getInt("id")?:0
            PatriotsView(navController,id)
        }
        composable("STEELERS/{id}",arguments =
            listOf(navArgument("id")
            {type= NavType.IntType })){
            val id=it.arguments?.getInt("id")?:0
            SteelersView(navController,id)
        }
        composable("TAMPA/{id}",arguments =
            listOf(navArgument("id")
            {type= NavType.IntType })){
            val id=it.arguments?.getInt("id")?:0
            TampaView(navController,id)
        }

        composable("Splash"){
            SplashScreen(navController)
        }

    }
}