package com.diiage.template.domain.model

/**
 * Module de domaine représentant une prévision météo.
 */
data class Weather(
    val id: Long = 0,
    val location: String,
    val region: String,
    val country: String,
    val currentTemp: Double,
    val condition: String,
    val iconUrl: String,
    val date: String,
    val maxTemp: Double,
    val minTemp: Double
)