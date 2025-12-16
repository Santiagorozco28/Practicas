package com.example.sportsapi.di

import com.example.sportsapi.data.NflApiService
import com.example.sportsapi.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    // 1. Proveer cliente OkHttpClient con Interceptor
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    // Añadimos la clave y el host de la API de forma global
                    .addHeader("X-RapidAPI-Key", Constants.API_KEY)
                    .addHeader("X-RapidAPI-Host", Constants.API_HOST)
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    @Singleton
    @Provides
    // 2. Proveer Retrofit usando el OkHttpClient modificado
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient) // Usamos el cliente que inyecta los headers
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    // 3. Proveer el servicio de la NFL
    fun providesNflApiService(retrofit: Retrofit): NflApiService {
        return retrofit.create(NflApiService::class.java)
    }
}