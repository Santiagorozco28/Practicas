package com.example.proyectos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
                    Calculator()
                }
            }
        }
    }
}

@Composable
fun Calculator() {
    var expression by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Pantalla
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(8.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = expression,
                fontSize = 32.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
            Text(
                text = result,
                fontSize = 24.sp,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }

        // Teclado
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            val buttons = listOf(
                listOf("7", "8", "9", "÷"),
                listOf("4", "5", "6", "×"),
                listOf("1", "2", "3", "-"),
                listOf("0", "DEL", "=", "+")
            )

            buttons.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    row.forEach { symbol ->
                        Button(
                            onClick = {
                                when (symbol) {
                                    "DEL" -> {
                                        if (expression.isNotEmpty())
                                            expression = expression.dropLast(1)
                                    }
                                    "=" -> {
                                        try {
                                            val sanitized = expression
                                                .replace("÷", "/")
                                                .replace("×", "*")
                                            val evalResult = evalExpression(sanitized)
                                            result = evalResult.toString()
                                        } catch (e: Exception) {
                                            result = "Error"
                                        }
                                    }
                                    else -> expression += symbol
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(70.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (symbol in listOf("+", "-", "×", "÷", "="))
                                    Color(0xFF2196F3) else Color(0xFF424242),
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = symbol,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

// Función para evaluar la expresión (muy básica)
fun evalExpression(expr: String): Double {
    try {
        var cleanExpr = expr.replace("÷", "/").replace("×", "*")

        // Busca el primer operador (+, -, *, /)
        val operator = cleanExpr.firstOrNull { it in "+-*/" }
            ?: return cleanExpr.toDouble() // Si no hay operador, solo devuelve el número

        val parts = cleanExpr.split(operator)
        if (parts.size != 2) return Double.NaN

        val a = parts[0].toDouble()
        val b = parts[1].toDouble()

        return when (operator) {
            '+' -> a + b
            '-' -> a - b
            '*' -> a * b
            '/' -> if (b != 0.0) a / b else Double.NaN
            else -> Double.NaN
        }
    } catch (e: Exception) {
        return Double.NaN
    }
}
