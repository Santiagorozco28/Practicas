package com.example.sportsapi.model

import com.google.gson.annotations.SerializedName

data class NflNewsResponse(
    @SerializedName("header")
    val header: String?, // Ej: "NFL News"

    @SerializedName("link")
    val link: Link?, // Objeto que contiene el enlace a la fuente principal

    @SerializedName("articles")
    val articles: List<Article> // La lista real de noticias
)

data class Article(
    @SerializedName("id")
    val id: Int,

    @SerializedName("headline")
    val headline: String?, // El título principal

    @SerializedName("description")
    val description: String?, // El resumen o descripción

    @SerializedName("published")
    val published: String?, // Fecha y hora de publicación (ej: "2025-12-11T03:04:20Z")

    @SerializedName("images")
    val images: List<ArticleImage>?, // Lista de imágenes relacionadas

    @SerializedName("premium")
    val premium: Boolean?, // Si el contenido es premium

    @SerializedName("byline")
    val byline: String? // El autor o fuente
)

data class ArticleImage(
    @SerializedName("url")
    val url: String?, // La URL de la imagen que se puede cargar (del JSON de Noticias)

    @SerializedName("name")
    val name: String?,

    @SerializedName("caption")
    val caption: String?,

    @SerializedName("height")
    val height: Int?,

    @SerializedName("width")
    val width: Int?
)

data class Link(
    @SerializedName("href")
    val href: String?,
    @SerializedName("text")
    val text: String?
)

data class NflTeamResponse(
    @SerializedName("team")
    val team: Team
)

/**
 * Clase que contiene los detalles del equipo.
 */
data class Team(
    @SerializedName("id")
    val id: String?,

    @SerializedName("abbreviation")
    val abbreviation: String?, // ARI, ATL, BAL, etc.

    @SerializedName("displayName")
    val displayName: String?, // Arizona Cardinals, Atlanta Falcons, etc.

    @SerializedName("shortDisplayName")
    val shortDisplayName: String?, // Cardinals, Falcons, etc.

    @SerializedName("location")
    val location: String?, // Arizona, Atlanta, etc.

    @SerializedName("color")
    val color: String?, // Código de color (ej: a40227)

    @SerializedName("logos")
    val logos: List<Logo>?, // Lista de URLs de logos

    @SerializedName("links")
    val links: List<Link>? // Links relacionados (clubhouse, schedule, etc.)
)

/**
 * Clase que contiene la información de los logos.
 * Nota: El campo de URL en la API de Equipos es "href"
 */
data class Logo(
    @SerializedName("href")
    val href: String?, // URL del logo (del JSON de Equipos)

    @SerializedName("alt")
    val alt: String?,

    @SerializedName("rel")
    val rel: List<String>?, // Tipo de logo (ej: "full", "default")

    @SerializedName("width")
    val width: Int?,

    @SerializedName("height")
    val height: Int?
)