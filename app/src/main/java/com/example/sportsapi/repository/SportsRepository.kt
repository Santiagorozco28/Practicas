package com.example.sportsapi.repository

import android.util.Log
import com.example.sportsapi.data.NflApiService // La nueva interfaz API
import com.example.sportsapi.model.Article
import com.example.sportsapi.model.Team
import com.example.sportsapi.model.NflTeamResponse // Necesario para el mapeo
import javax.inject.Inject

/**
 * Repositorio central de la aplicación NFL
 * Se encarga de llamar a los endpoints de la API y de transformar los datos
 * a los modelos limpios (Article, Team).
 */
class NflRepository @Inject constructor(
    private val nflApiService: NflApiService // Fuente de datos remota
    // Eliminamos MovieDao ya que no hay base de datos local para favoritos en esta nueva API
) {
    private val TAG = "NFL_REPO"

    // --- SECCIÓN DE DATOS REMOTOS (API) ---

    /**
     * Obtiene el feed de noticias de la NFL (usado en la Pestaña 0).
     * @return Una lista de objetos Article. Si falla, retorna una lista vacía.
     */
    suspend fun getLatestNews(): List<Article> {
        return try {
            val response = nflApiService.getLatestNflNews()

            if (response.isSuccessful) {
                // Extrae la lista 'articles' del cuerpo de la respuesta NflNewsResponse
                response.body()?.articles ?: emptyList()
            } else {
                Log.e(TAG, "Error API Noticias: Code ${response.code()}, Body: ${response.errorBody()?.string()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error de red/conexión Noticias: ${e.message}")
            emptyList()
        }
    }

    /**
     * Obtiene la lista de todos los equipos de la NFL (usado en la Pestaña 1).
     * @return Una lista de objetos Team. Si falla, retorna una lista vacía.
     */
    suspend fun getAllTeams(): List<Team> {
        return try {
            val response = nflApiService.getAllNflTeams()

            if (response.isSuccessful) {
                // La respuesta es List<NflTeamResponse>, mapeamos a List<Team>
                response.body()?.map { it.team } ?: emptyList()
            } else {
                Log.e(TAG, "Error API Equipos: Code ${response.code()}, Body: ${response.errorBody()?.string()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error de red/conexión Equipos: ${e.message}")
            emptyList()
        }
    }
}