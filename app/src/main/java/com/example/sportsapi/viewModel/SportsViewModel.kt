package com.example.sportsapi.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsapi.model.Team
import com.example.sportsapi.repository.NflRepository
import com.example.sportsapi.state.NflNewsItemState
import com.example.sportsapi.state.NflUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// Nuevo ViewModel para manejar los dos feeds de la NFL
@HiltViewModel
class NflAppViewModel @Inject constructor(
    private val repository: NflRepository
) : ViewModel() {

    // --- ESTADOS DE LA UI OBSERVABLES ---
    private val _uiState = MutableStateFlow(NflUiState())
    val uiState: StateFlow<NflUiState> = _uiState.asStateFlow()

    // Controla la pestaña actualmente seleccionada (0: Noticias, 1: Equipos)
    private val _selectedCategory = MutableStateFlow(0)
    val selectedCategory: StateFlow<Int> = _selectedCategory.asStateFlow()

    // Variable de caché interna para la búsqueda y la persistencia simple
    private var originalNewsList = emptyList<NflNewsItemState>()
    private var originalTeamList = emptyList<Team>()

    private val _selectedTeam = MutableStateFlow<Team?>(null)
    val selectedTeam: StateFlow<Team?> = _selectedTeam.asStateFlow()

    // Eliminamos todo el estado y lógica de favoritos (_favoriteFilter, _selectedMovie, etc.)

    init {
        // Carga inicial al arrancar el ViewModel
        fetchData(0) // Carga Noticias por defecto
        fetchData(1) // Carga Equipos
    }

    // --- LÓGICA DE CATEGORÍAS (PESTAÑAS) ---

    // Función de inicialización/cambio de pestaña, similar a tu changeCategory anterior
    fun changeCategory(index: Int) {
        _selectedCategory.value = index
        // Limpiamos la búsqueda y forzamos la carga/muestra de datos para la pestaña.
        onSearchChange("", index)
    }

    // --- LÓGICA CENTRAL DE CARGA DE DATOS ---

    // Lógica unificada para cargar Noticias o Equipos
    private fun fetchData(categoryIndex: Int) {
        when (categoryIndex) {
            0 -> fetchLatestNews()
            1 -> fetchAllTeams()
        }
    }

    private fun fetchLatestNews() {
        // Solo recargar si no estamos cargando ya
        if (_uiState.value.isNewsLoading) return

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isNewsLoading = true, newsError = null) }

            // 1. Obtener la lista del Repositorio
            val articles = repository.getLatestNews()

            if (articles.isNotEmpty()) {
                // 2. Mapear los modelos de la API (Article) a modelos de Estado (NflNewsItemState)
                val newsItems = articles.map { NflNewsItemState.fromArticle(it) }

                originalNewsList = newsItems // Guardar en caché interna
                _uiState.update {
                    it.copy(isNewsLoading = false, newsList = newsItems)
                }
            } else {
                // Manejo de error/lista vacía
                _uiState.update {
                    it.copy(
                        isNewsLoading = false,
                        // Muestra el mensaje de error del repositorio o un genérico
                        newsError = if (repository.getLatestNews().isEmpty()) "No se pudieron cargar las noticias." else null
                    )
                }
            }
        }
    }

    private fun fetchAllTeams() {
        if (_uiState.value.isTeamsLoading) return

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isTeamsLoading = true, teamsError = null) }

            // 1. Obtener la lista del Repositorio
            val teams = repository.getAllTeams()

            if (teams.isNotEmpty()) {
                originalTeamList = teams // Guardar en caché interna
                _uiState.update {
                    it.copy(isTeamsLoading = false, teamsList = teams)
                }
            } else {
                // Manejo de error/lista vacía
                _uiState.update {
                    it.copy(
                        isTeamsLoading = false,
                        teamsError = if (repository.getAllTeams().isEmpty()) "No se pudieron cargar los equipos." else null
                    )
                }
            }
        }
    }

    // --- LÓGICA DE BÚSQUEDA EN TIEMPO REAL ---

    fun onSearchChange(query: String, currentIndex: Int) {
        // Actualiza el campo de búsqueda para el TextField en la UI
        _uiState.update { it.copy(searchQuery = query) }

        if (query.isEmpty()) {
            // Si la búsqueda está vacía, restaurar ambas listas a su estado original
            _uiState.update {
                it.copy(newsList = originalNewsList, teamsList = originalTeamList)
            }
            return
        }

        // Aplicar el filtro solo a la lista activa
        when (currentIndex) {
            0 -> { // Pestaña: Noticias
                val filteredNews = originalNewsList.filter { item ->
                    item.headline.contains(query, ignoreCase = true)
                }
                _uiState.update { it.copy(newsList = filteredNews) }
            }
            1 -> { // Pestaña: Equipos
                val filteredTeams = originalTeamList.filter { team ->
                    team.displayName?.contains(query, ignoreCase = true) == true ||
                            team.abbreviation?.contains(query, ignoreCase = true) == true
                }
                _uiState.update { it.copy(teamsList = filteredTeams) }
            }
        }
    }
    fun getTeamById(teamId: String) {
        // CRÍTICO: Limpiar el estado anterior para forzar la carga en la UI
        _selectedTeam.value = null

        viewModelScope.launch(Dispatchers.IO) {

            // 1. Buscamos en la caché
            val team = originalTeamList.find { it.id == teamId }

            if (team == null) {
                // Log de Fallo: Muestra el tamaño de la lista
                Log.e("NFL_DETAIL_FAIL", "Equipo con ID $teamId NO encontrado. Caché tamaño: ${originalTeamList.size}")
            } else {
                // Log de Éxito
                Log.i("NFL_DETAIL_SUCCESS", "Equipo encontrado: ${team.displayName}")
            }

            // 2. Actualizamos el estado (será null si falló la búsqueda)
            _selectedTeam.value = team
        }
    }
}