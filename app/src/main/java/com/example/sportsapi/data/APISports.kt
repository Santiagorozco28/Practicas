package com.example.sportsapi.data

import com.example.sportsapi.model.NflNewsResponse
import com.example.sportsapi.model.NflTeamResponse
import retrofit2.Response
import retrofit2.http.GET
// No necesitamos @Header aquí

interface NflApiService {

    @GET("nfl-team-listing/v1/data")
    suspend fun getAllNflTeams(): Response<List<NflTeamResponse>>

    @GET("nfl-news")
    suspend fun getLatestNflNews(): Response<NflNewsResponse>

}