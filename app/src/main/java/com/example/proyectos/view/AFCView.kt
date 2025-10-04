package com.example.nfl.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectos.R
import com.example.nfl.components.MainButton
import com.example.nfl.components.MainIconButton
import com.example.nfl.components.Space
import com.example.nfl.components.TextView
import com.example.nfl.components.TitleBar

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AFCView(navController: NavController,id:Int){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { TitleBar("AFC") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF960C0C)
                )
                ,navigationIcon= {
                    MainIconButton(icon = Icons.Default.ArrowBack) {
                        navController.popBackStack()
                    }
                }
            )
        }

    ){
        ContentAFCView(navController)
    }

}

@Composable
fun ContentAFCView(navController: NavController) {
    val id = 20
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 100.dp, bottom = 1.dp), // menos padding que antes para no empujar tanto
        verticalArrangement = Arrangement.spacedBy(5.dp), // separa cada equipo
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // PATRIOTS
        Image(
            painter = painterResource(id = R.drawable.patriots),
            contentDescription = null,
            modifier = Modifier.size(100.dp) // tamaño grande
        )
        MainButton(
            name = "NEW ENGLAND PATRIOTS",
            backColor = Color(0xFF27368A),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth(0.8f) // ancho al 80% de la pantalla
                .height(60.dp)      // alto más grande
        ) {
            navController.navigate("PATRIOTS/${id}")
        }

        // STEELERS
        Image(
            painter = painterResource(id = R.drawable.steelers),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        MainButton(
            name = "PITSBURGH STEELERS",
            backColor = Color(0xFFFFC107),
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(60.dp)
        ) {
            navController.navigate("STEELERS/${id + 1}")
        }

        // KANSAS
        Image(
            painter = painterResource(id = R.drawable.kansas),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        MainButton(
            name = "KANSAS CITY CHIEFS",
            backColor = Color.Red,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(60.dp)
        ) {
            navController.navigate("KANSAS/${id}")
        }

        // JAGUARS
        Image(
            painter = painterResource(id = R.drawable.jaguars),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        MainButton(
            name = "JACKSONVILLE JAGUARS",
            backColor = Color(0xFF0E8A7F),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(60.dp)
        ) {
            navController.navigate("JAGUARS/${id + 1}")
        }
    }
}
