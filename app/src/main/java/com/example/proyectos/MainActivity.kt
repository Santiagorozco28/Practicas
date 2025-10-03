package com.example.proyectos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                    ISRCalculator()
                }
            }
        }
    }
}

@Composable
fun ISRCalculator() {
    var sueldo by remember { mutableStateOf("") }
    var isr by remember { mutableStateOf("") }
    var sueldoNeto by remember { mutableStateOf("") }

    Row() { Image( painter = painterResource(id = R.drawable.sad), contentDescription = null ) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Cálculo de ISR Quincenal",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Campo para sueldo
        OutlinedTextField(
            value = sueldo,
            onValueChange = { sueldo = it },
            label = { Text("Sueldo Bruto Quincenal") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón calcular
        Button(
            onClick = {
                val sueldoDouble = sueldo.toDoubleOrNull() ?: 0.0
                val isrCalculado = calcularISRQuincenal(sueldoDouble)
                isr = String.format("%.2f", isrCalculado)
                sueldoNeto = String.format("%.2f", sueldoDouble - isrCalculado)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular ISR")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Resultados
        ResultField(label = "ISR Calculado", value = isr)
        Spacer(modifier = Modifier.height(12.dp))
        ResultField(label = "Sueldo Neto", value = sueldoNeto)
    }
}

@Composable
fun ResultField(label: String, value: String) {
    OutlinedTextField(
        value = value,
        onValueChange = { },
        label = { Text(label) },
        readOnly = true,
        modifier = Modifier.fillMaxWidth()
    )
}

fun calcularISRQuincenal(sueldo: Double): Double {
    val tablaISR = listOf(
        ISRRow(0.01, 368.10, 0.00, 1.92),
        ISRRow(368.11, 3124.35, 7.05, 6.40),
        ISRRow(3124.36, 5490.75, 183.45, 10.88),
        ISRRow(5490.76, 6382.80, 441.00, 16.00),
        ISRRow(6382.81, 7641.90, 583.65, 17.92),
        ISRRow(7641.91, 15412.80, 809.25, 21.36),
        ISRRow(15412.81, 24292.65, 2469.15, 23.52),
        ISRRow(24292.66, 46378.50, 4557.75, 30.00),
        ISRRow(46378.51, 61838.10, 11183.40, 32.00),
        ISRRow(61838.11, 185514.30, 16130.55, 34.00),
        ISRRow(185514.31, Double.MAX_VALUE, 58180.35, 35.00)
    )

    val fila = tablaISR.find { sueldo in it.limiteInferior..it.limiteSuperior }
        ?: return 0.0

    val excedente = sueldo - fila.limiteInferior
    return fila.cuotaFija + (excedente * (fila.porcentaje / 100.0))
}

data class ISRRow(
    val limiteInferior: Double,
    val limiteSuperior: Double,
    val cuotaFija: Double,
    val porcentaje: Double
)
