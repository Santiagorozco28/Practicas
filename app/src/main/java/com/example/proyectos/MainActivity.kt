package com.example.proyectos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
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

    Row() {
        Image(
            painter = painterResource(id = R.drawable.sad),
            contentDescription = null
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Campo para el sueldo
        OutlinedTextField(
            value = sueldo,
            onValueChange = { sueldo = it },
            label = { Text("Sueldo Bruto") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para calcular
        Button(
            onClick = {
                val sueldoDouble = sueldo.toDoubleOrNull() ?: 0.0
                val isrCalculado = calcularISR(sueldoDouble)
                isr = String.format("%.2f", isrCalculado)
                sueldoNeto = String.format("%.2f", sueldoDouble - isrCalculado)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para mostrar ISR calculado
        OutlinedTextField(
            value = isr,
            onValueChange = { },
            label = { Text("ISR Calculado") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para mostrar sueldo neto
        OutlinedTextField(
            value = sueldoNeto,
            onValueChange = { },
            label = { Text("Sueldo Neto") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// Función para calcular ISR con la tabla 2025
fun calcularISR(sueldo: Double): Double {
    val tablaISR = listOf(
        ISRRow(0.01, 8952.49, 0.00, 1.92),
        ISRRow(8952.50, 75984.55, 171.88, 6.40),
        ISRRow(75984.56, 133536.07, 4461.94, 10.88),
        ISRRow(133536.08, 155229.80, 10723.55, 16.00),
        ISRRow(155229.81, 185852.57, 14194.54, 17.92),
        ISRRow(185852.58, 374837.88, 19682.13, 21.36),
        ISRRow(374837.89, 590795.99, 60049.40, 23.52),
        ISRRow(590796.00, 1127926.84, 110842.74, 30.00),
        ISRRow(1127926.85, 1503902.46, 271981.99, 32.00),
        ISRRow(1503902.47, 4511707.37, 392294.17, 34.00),
        ISRRow(4511707.38, Double.MAX_VALUE, 1414947.85, 35.00)
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