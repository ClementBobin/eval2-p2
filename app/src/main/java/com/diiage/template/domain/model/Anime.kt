package com.diiage.template.domain.model

/**
 * Module de domaine représentant une prévision météo.
 */
data class Anime(
    val id: Long, // Maps to mal_id
    val title: String,
    val englishTitle: String?,
    val imageUrl: String?, // Will map from images.jpg.image_url
    val episodes: Int?,
    val status: String?,
    val score: Double?,
    val type: String?,
    val year: Int?
)