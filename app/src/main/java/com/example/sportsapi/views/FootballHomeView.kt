package com.example.sportsapi.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sportsapi.components.NewsArticleCard // Componente para Noticias
import com.example.sportsapi.components.TeamCard // Componente para Equipos
import com.example.sportsapi.state.NflUiState // El nuevo estado unificado
import com.example.sportsapi.viewModel.NflAppViewModel // El nuevo ViewModel
import com.example.sportsapi.state.NflNewsItemState
import com.example.sportsapi.model.Team
import com.example.sportsapi.utils.Constants.CUSTOM_BLACK
import com.example.sportsapi.utils.Constants.CUSTOM_RED
import kotlinx.coroutines.launch

// Colores de tu Constants.kt
val PrimaryDark = Color(0xFF001E45)
val HighlightRed = Color(0xFFAF1212)
val TextWhite = Color.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(viewModel: NflAppViewModel = hiltViewModel(), navController: NavController) {

    val uiState by viewModel.uiState.collectAsState()
    val searchQuery = uiState.searchQuery

    // Definición de las pestañas disponibles
    val categories = listOf("NOTICIAS", "EQUIPOS")

    val pagerState = rememberPagerState(pageCount = { categories.size })
    val scope = rememberCoroutineScope()

    // SINCRONIZACION: Al cambiar de pestaña, limpiamos la búsqueda y el filtro.
    LaunchedEffect(pagerState.currentPage) {
        viewModel.onSearchChange(query = "", currentIndex = pagerState.currentPage)
    }

    // ESTRUCTURA DE UI
    Scaffold(
        containerColor = PrimaryDark,
        topBar = {
            Column(
                modifier = Modifier
                    .background(PrimaryDark)
                    .padding(top = 40.dp, bottom = 10.dp)
            ) {
                // Título de la App
                Text(
                    text = "NFL App",
                    color = HighlightRed,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                )

                // Componente de Búsqueda
                TextField(
                    value = searchQuery,
                    onValueChange = { viewModel.onSearchChange(it, pagerState.currentPage) },
                    label = { Text("Buscar en ${categories[pagerState.currentPage]}", color = Color.White) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = PrimaryDark,
                        unfocusedContainerColor = PrimaryDark,
                        cursorColor = HighlightRed,
                        focusedIndicatorColor = HighlightRed,
                        unfocusedIndicatorColor = Color.White
                    )
                )

                // BARRA DE PESTAÑAS
                ScrollableTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    containerColor = Color.Transparent,
                    contentColor = TextWhite,
                    edgePadding = 16.dp,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                            height = 3.dp,
                            color = HighlightRed
                        )
                    },
                    divider = {} // Elimina la línea divisoria por defecto
                ) {
                    categories.forEachIndexed { index, title ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                scope.launch {
                                    viewModel.changeCategory(index) // Forzamos el cambio en el VM para la sincronización
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = {
                                Text(
                                    text = title,
                                    fontWeight = if (pagerState.currentPage == index) FontWeight.Bold else FontWeight.Normal,
                                    color = if (pagerState.currentPage == index) TextWhite else Color.Gray
                                )
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->

        // CONTENIDO DESLIZABLE
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) { pageIndex ->
            // Renderiza el contenido de la página actual
            ContentHomeView(uiState, navController, viewModel, pageIndex)
        }
    }
}

/**
 * Contenido interno de la pantalla (Lista de Noticias o Equipos).
 * [VERSION SIMPLIFICADA SIN COMPONENTES DE ERROR/SHIMMER]
 */
@Composable
fun ContentHomeView(
    uiState: NflUiState,
    navController: NavController,
    viewModel: NflAppViewModel,
    currentIndex: Int // 0: Noticias, 1: Equipos
) {
    // Determinamos qué estado usar basado en la pestaña activa
    val isLoading = if (currentIndex == 0) uiState.isNewsLoading else uiState.isTeamsLoading
    val listToDisplay = if (currentIndex == 0) uiState.newsList else uiState.teamsList

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryDark)
    ) {
        if (isLoading) {
            // ESTADO DE CARGA: Muestra un indicador de progreso simple
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = HighlightRed
            )
        } else if (listToDisplay.isEmpty()) {
            // ESTADO DE LISTA VACÍA (Esto cubre los errores si el repositorio devuelve lista vacía)
            Text(
                text = "No se encontraron resultados. Verifica tu clave API o conexión.",
                color = TextWhite,
                modifier = Modifier.align(Alignment.Center).padding(16.dp)
            )
        } else {
            // ESTADO DE ÉXITO: Muestra la lista de la pestaña activa
            LazyColumn(
                contentPadding = PaddingValues(vertical = 10.dp)
            ) {
                if (currentIndex == 0) {
                    // PESTAÑA: NOTICIAS
                    items(uiState.newsList, key = { it.headline }) { articleState ->
                        NewsArticleCard(itemState = articleState) {
                            // Lógica de click (si aplica)
                        }
                    }
                }else if (currentIndex == 1) {
                    // PESTAÑA: EQUIPOS
                    items(uiState.teamsList, key = { it.id ?: "" }) { team ->
                        TeamCard(team = team) {
                            val teamId = team.id
                            if (teamId != null) {
                                // Navegación a la nueva ruta
                                navController.navigate("TeamDetail/$teamId")
                            }
                        }
                    }
                }
            }
        }
    }
}