package com.example.proyectos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyectos.ui.theme.ProyectosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectosTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(
                    )
                }

            }
        }
    }
}

@Composable
fun Greeting() {
    val context= LocalContext.current
    var ValorA by remember { mutableStateOf("") }
    var ValorB by remember { mutableStateOf("") }
    var Resultado by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(Modifier.padding(16.dp)){
            OutlinedTextField(
                value=ValorA,
                label={Text("Primer Valor")},
                onValueChange ={ValorA=it}
            )
        }
        Row(Modifier.padding(16.dp)){
            OutlinedTextField(
                value=ValorB,
                label={Text("Segundo Valor")},
                onValueChange ={ValorB=it}
            )
        }
        Row(
            Modifier.align(Alignment.CenterHorizontally)
        ){
            OutlinedButton(onClick = { /*TODO*/
                val a=ValorA.toInt()
                val b=ValorB.toInt()
                val c=a+b
                Resultado=c.toString()

            }) {
                Text(text = "Enviar")
            }
        }
        Row(
            modifier=Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally
                )
        ){
            OutlinedTextField(
                value=Resultado,
                label={Text("Resultado")},
                onValueChange ={Resultado=it}
            )
        }

    }
}