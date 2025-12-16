package com.example.sportsapi.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.sportsapi.model.Article // Cambiamos el modelo a Article
import com.example.sportsapi.model.Team
import com.example.sportsapi.state.NflNewsItemState
import com.example.sportsapi.utils.Constants.CUSTOM_BLACK
import com.example.sportsapi.utils.Constants.CUSTOM_RED
import com.example.sportsapi.views.TextWhite

// Asegúrate de que tu archivo Constants.kt y los colores sigan funcionando

// Colores de ejemplo (Ajusta esto a tus constantes reales)
val NFL_PRIMARY_BLUE = Color(0xFF003049) // Azul Oscuro para fondo
val NFL_SECONDARY_RED = Color(0xFFD62828) // Rojo para destacar

/**
 * Top Bar genérica. Se mantiene la estructura ya que es reutilizable.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    title: String,
    showBackButton: Boolean,
    onClickBackButton: () -> Unit,
    onClickAction: () -> Unit
) {
    TopAppBar(
        title = { Text(title, color = Color.White, fontWeight = FontWeight.Bold) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(CUSTOM_BLACK)),
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onClickBackButton) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás", tint = Color(CUSTOM_RED))
                }
            }
        }
    )
}

/**
 * Card para mostrar un artículo de noticias.
 * Reemplaza CardGame.
 */
@Composable
fun NewsArticleCard(itemState: NflNewsItemState, onClick: () -> Unit) {
    // Intentamos obtener la URL de la imagen directamente del estado
    val imageUrl = itemState.imageUrl

    // ... el resto de la lógica de la tarjeta ...

    // 1. Imagen destacada
    if (imageUrl.isNotEmpty()) {
        ArticleImage(imageUrl = imageUrl)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        // 1. TITULAR (Headline)
        Text(
            text = itemState.headline,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            // --- CAMBIO AQUÍ: Usar el color que desees ---
            color = NFL_SECONDARY_RED // ¡Cambiado de azul a rojo!
        )
        Spacer(modifier = Modifier.height(6.dp))

        // 2. DESCRIPCIÓN/RESUMEN
        Text(
            text = itemState.description,
            fontSize = 14.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            // --- CAMBIO AQUÍ: Usar un color diferente ---
            color = TextWhite // Usamos blanco para la descripción (si el fondo de la tarjeta es oscuro)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(/* ... */) {
            Text(
                text = itemState.author,
                fontSize = 12.sp,
                color = Color.Gray // Mantener gris o cambiar a TextWhite.copy(alpha = 0.7f)
            )
            Text(text = itemState.publishedDate, /* ... */)
        }
    }
}

/**
 * Componente de Imagen (Reemplaza MainImage y ahora toma la URL de la imagen del artículo)
 */
@Composable
fun ArticleImage(imageUrl: String) {
    val painter = rememberAsyncImagePainter(model = imageUrl)

    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
    )
}

/**
 * Componente para botón de Sitio Web (Reutilizado, ajustado el color)
 */
@Composable
fun ArticleLinkButton(url: String) {

    val context = LocalContext.current
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { context.startActivity(intent) },
            colors = ButtonDefaults.buttonColors(
                containerColor = NFL_SECONDARY_RED, // Color de acento
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text(text = "Leer Artículo Completo")
        }
    }
}

@Composable
fun TeamLogo(url: String) {
    val painter = rememberAsyncImagePainter(model = url)
    Image(
        painter = painter,
        contentDescription = "Logo del equipo",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .size(48.dp) // Tamaño pequeño para el logo
            .clip(RoundedCornerShape(8.dp))
    )
}

@Composable
fun TeamCard(team: Team, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 1. Obtener la URL del logo (usando el primer 'href' disponible)
            val logoUrl = team.logos?.firstOrNull()?.href ?: ""
            if (logoUrl.isNotEmpty()) {
                TeamLogo(url = logoUrl) // Llama al componente de imagen
            }

            Spacer(Modifier.width(16.dp))

            Column {
                Text(
                    text = team.displayName ?: "Equipo Desconocido",
                    color = TextWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(text = "Abreviatura: ${team.abbreviation}", color = Color.LightGray)
                Text(text = "Ubicación: ${team.location}", color = Color.LightGray)
            }
        }
    }
}