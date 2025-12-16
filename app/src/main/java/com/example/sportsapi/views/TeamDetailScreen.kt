package com.example.sportsapi.views

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.sportsapi.components.MainTopBar
import com.example.sportsapi.model.Team
import com.example.sportsapi.utils.Constants.CUSTOM_BLACK
import com.example.sportsapi.utils.Constants.CUSTOM_RED
import com.example.sportsapi.viewModel.NflAppViewModel


// Necesitas definir este componente en un archivo separado (ej: components/MainTopBar.kt)
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun TeamDetailScreen(
    teamId: String,
    navController: NavController,
    viewModel: NflAppViewModel = hiltViewModel()
) {
    val selectedTeam by viewModel.selectedTeam.collectAsState()

    LaunchedEffect(teamId) {
        if (teamId.isNotEmpty()) {
            viewModel.getTeamById(teamId)
        }
    }

    Scaffold(
        topBar = {
            // Título dinámico, si el equipo es null, se usa el título por defecto.
            MainTopBar(
                title = selectedTeam?.displayName ?: "Detalle del Equipo",
                showBackButton = true,
                onClickBackButton = { navController.popBackStack() },
                onClickAction = { /* No hay acción secundaria */ }
            )
        },
        containerColor = Color(CUSTOM_BLACK)
    ) { paddingValues ->

        val team = selectedTeam

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (team == null) {
                // Muestra un indicador de carga centrado
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(color = Color(CUSTOM_RED))
                    Spacer(Modifier.height(16.dp))
                    Text("Buscando detalles del equipo...", color = Color.LightGray)
                }
            } else {
                // Si el equipo está cargado, muestra el contenido detallado
                TeamDetailContent(team)
            }
        }
    }
}

// ... Las funciones TeamDetailContent, TeamLogoLarge y DetailRow deben estar aquí o importadas ...

@Composable
fun TeamDetailContent(team: Team) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        // --- 1. Logo Principal ---
        item {
            val logoUrl = team.logos?.firstOrNull()?.href
            if (logoUrl != null) {
                TeamLogoLarge(url = logoUrl)
            }
            Spacer(Modifier.height(24.dp))
        }

        // --- 2. Información Principal ---
        item {
            // Intentamos parsear el color hexadecimal
            val teamColor = team.color?.let {
                try { Color(android.graphics.Color.parseColor("#$it")) }
                catch (e: Exception) { Color.White }
            } ?: Color.White

            Text(
                text = team.displayName ?: "Equipo Desconocido",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = teamColor
            )
            Text(
                text = team.location ?: "Ubicación Desconocida",
                fontSize = 18.sp,
                color = Color.LightGray
            )
            Spacer(Modifier.height(24.dp))
        }

        // --- 3. Tarjeta de Métricas ---
        item {
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(CUSTOM_BLACK))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    DetailRow(label = "Abreviatura", value = team.abbreviation ?: "N/A", textColor = Color(CUSTOM_RED))
                    DetailRow(label = "Apodo", value = team.displayName ?: "N/A")
                    DetailRow(label = "ID Interno", value = team.id ?: "N/A")
                }
            }
            Spacer(Modifier.height(24.dp))
        }

        // --- 4. Enlaces de Navegación ---
        item {
            Text(
                text = "Enlaces Oficiales:",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )
        }

        team.links?.forEach { link ->
            if (link.href != null && link.text != null) {
                item {
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link.href))
                            context.startActivity(intent)
                        },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(CUSTOM_RED))
                    ) {
                        Text("Abrir ${link.text}", color = Color.White)
                    }
                }
            }
        }
        item { Spacer(Modifier.height(32.dp)) }
    }
}

@Composable
fun TeamLogoLarge(url: String) {
    // ... (El mismo código que antes para mostrar la imagen grande)
    val painter = rememberAsyncImagePainter(model = url)
    Image(
        painter = painter,
        contentDescription = "Logo del equipo",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .size(150.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.9f))
            .padding(8.dp)
    )
}

@Composable
fun DetailRow(label: String, value: String, textColor: Color = Color.White) {
    // ... (El mismo código que antes para las filas de detalle)
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$label:", fontWeight = FontWeight.SemiBold, color = Color.Gray)
        Text(text = value, color = textColor, fontWeight = FontWeight.Medium)
    }
}