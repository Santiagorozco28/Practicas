package com.example.nfl.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.Navigator
import com.example.nfl.components.ActionButton
import com.example.nfl.components.MainButton
import com.example.nfl.components.Space
import com.example.nfl.components.TextView
import com.example.nfl.components.TitleBar
import com.example.proyectos.R


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NFLView(navController: NavController){
    Scaffold(){
        ContentNFLView(navController)
    }
}
@Composable
fun ContentNFLView(navController: NavController){
    val id = 10
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // --- Lado izquierdo: AFC ---
        Box(
            modifier = Modifier
                .weight(1f) // ocupa la mitad de la pantalla
                .fillMaxHeight()
                .background(Color(0xFF960C0C)), // color de fondo de la AFC
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.am),
                    contentDescription = null,
                    modifier = Modifier.size(150.dp)
                )
                Space(espacio = 10)
                MainButton(
                    name = "AFC",
                    backColor = Color.White,
                    color = Color(0xFF960C0C),
                    modifier = Modifier
                ) {
                    navController.navigate("AFC/$id")
                }
            }
        }

        // --- Lado derecho: NFC ---
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color(0xFF192688)), // color de fondo de la NFC
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.nac),
                    contentDescription = null,
                    modifier = Modifier.size(150.dp)
                )
                Space(espacio = 10)
                MainButton(
                    name = "NFC",
                    backColor = Color.White,
                    color = Color(0xFF192688),
                    modifier = Modifier
                ) {
                    navController.navigate("NFC/$id")
                }
            }
        }
    }
}
