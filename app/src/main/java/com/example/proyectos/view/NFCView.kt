package com.example.nfl.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
fun NFCView(navController: NavController,id:Int){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { TitleBar("NFC") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF192688)
                )
                ,navigationIcon= {
                    MainIconButton(icon = Icons.Default.ArrowBack) {
                        navController.popBackStack()
                    }
                }
            )
        }

    ){
        ContentNFCView(navController)
    }

}

@Composable
fun ContentNFCView(navController: NavController) {
    val id = 20
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 100.dp, bottom = 1.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp), // separa cada equipo
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // PATRIOTS
        Image(
            painter = painterResource(id = R.drawable.dallas),
            contentDescription = null,
            modifier = Modifier.size(100.dp) // tamaño grande
        )
        MainButton(
            name = "DALLAS COWBOYS",
            backColor = Color(0xFF3F51B5),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth(0.8f) // ancho al 80% de la pantalla
                .height(60.dp)      // alto más grande
        ) {
            navController.navigate("DALLAS/${id}")
        }

        // STEELERS
        Image(
            painter = painterResource(id = R.drawable.greenbay),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        MainButton(
            name = "GREEN BAY PACKERS",
            backColor = Color(0xFF296E31),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(60.dp)
        ) {
            navController.navigate("GREENBAY/${id + 1}")
        }

        // KANSAS
        Image(
            painter = painterResource(id = R.drawable.sf),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        MainButton(
            name = "SAN FRANCISCO 49ERS",
            backColor = Color(0xFF960C0C),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(60.dp)
        ) {
            navController.navigate("SF/${id}")
        }

        Image(
            painter = painterResource(id = R.drawable.tampa),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        MainButton(
            name = "TAMPA BAY BUCCANEERS",
            backColor = Color.Black,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(60.dp)
        ) {
            navController.navigate("TAMPA/${id + 1}")
        }
    }
}
