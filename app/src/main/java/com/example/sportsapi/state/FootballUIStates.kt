package com.example.sportsapi.state

import com.example.sportsapi.model.Article
import com.example.sportsapi.model.Team

data class NflNewsItemState(
    val headline: String,
    val description: String,
    val publishedDate: String,
    val author: String,
    val imageUrl: String,
    val articleUrl: String? = null // La URL del link principal si estuviera disponible
) {
    companion object {
        // Función de mapeo (Mapper) para convertir el modelo de la API (Article) a estado de UI.
        fun fromArticle(article: Article): NflNewsItemState {
            // Intentamos obtener la URL de la imagen del primer elemento de la lista 'images'
            val imageUrl = article.images?.firstOrNull()?.url ?: ""
            // Intentamos extraer solo la fecha (ignorando la hora T...)
            val date = article.published?.split("T")?.get(0) ?: "Fecha desconocida"

            return NflNewsItemState(
                headline = article.headline ?: "Título no disponible",
                description = article.description ?: "Contenido no disponible.",
                publishedDate = date,
                author = article.byline?.let { "Por $it" } ?: "Fuente Desconocida",
                imageUrl = imageUrl
            )
        }
    }
}

data class NflUiState(
    // ESTADO PARA LA PESTAÑA DE NOTICIAS
    val isNewsLoading: Boolean = false,
    val newsList: List<NflNewsItemState> = emptyList(),
    val newsError: String? = null,

    // ESTADO PARA LA PESTAÑA DE EQUIPOS
    val isTeamsLoading: Boolean = false,
    val teamsList: List<Team> = emptyList(), // Usamos el modelo Team directamente
    val teamsError: String? = null,

    // ESTADO DE BÚSQUEDA (General, si aplica)
    val searchQuery: String = ""
)