package com.diiage.template.data.repository

import com.diiage.template.data.remote.JikanApi
import com.diiage.template.data.remote.HttpException

import com.diiage.template.domain.model.Anime
import com.diiage.template.data.dto.AnimeDto
import com.diiage.template.domain.repository.AnimeRepository

/**
 * Implementation of [AnimeRepository] using Jikan API as the data source.
 *
 * This repository handles fetching top anime and searching for anime
 * by title, mapping the data transfer objects (DTOs) from the Jikan API
 * to the domain models used in the application.
 *
 * @property jikanApi The Jikan API service for making network requests.
 */
internal class AnimeRepositoryImpl(
    private val jikanApi: JikanApi
) : AnimeRepository {

    /**
     * Fetches the top anime list from Jikan API.
     *
     * Maps [AnimeDto] objects to domain [Anime] models with proper
     * error handling for network and API-specific errors.
     *
     * @param page The page number for pagination
     * @return List of [Anime] objects or empty list on error
     * @throws HttpException for network-related errors
     */
    override suspend fun getTopAnime(page: Int): List<Anime> {
        return try {
            val response = jikanApi.getTopAnime(page)
            response.data.map { dto ->
                Anime(
                    id = dto.malId,
                    title = dto.title,
                    englishTitle = dto.titleEnglish,
                    imageUrl = dto.images.jpg.largeImageUrl ?: dto.images.jpg.imageUrl,
                    episodes = dto.episodes,
                    status = dto.status,
                    score = dto.score,
                    type = dto.type,
                    year = dto.year
                )
            }
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Searches for anime by title query.
     *
     * Uses the Jikan search endpoint and maps results to domain models.
     * Includes Jikan API-specific error handling.
     *
     * @param query The search query string
     * @param page The page number for pagination
     * @return List of [Anime] objects matching the search or empty list
     * @throws HttpException for network-related errors
     */
    override suspend fun searchAnime(query: String, page: Int): List<Anime> {
        if (query.isBlank()) {
            return emptyList()
        }

        return try {
            val response = jikanApi.searchAnime(query, page)
            response.data.map { dto ->
                Anime(
                    id = dto.malId,
                    title = dto.title,
                    englishTitle = dto.titleEnglish,
                    imageUrl = dto.images.jpg.largeImageUrl ?: dto.images.jpg.imageUrl,
                    episodes = dto.episodes,
                    status = dto.status,
                    score = dto.score,
                    type = dto.type,
                    year = dto.year
                )
            }
        } catch (e: Exception) {
            throw e
        }
    }
}