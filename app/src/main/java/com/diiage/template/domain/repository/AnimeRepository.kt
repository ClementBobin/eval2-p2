package com.diiage.template.domain.repository

import com.diiage.template.domain.model.Anime

interface AnimeRepository {

    /**
     * Fetches the top anime list from Jikan API.
     *
     * @param page The page number for pagination (default: 1)
     * @return List of [Anime] objects representing top anime
     */
    suspend fun getTopAnime(page: Int = 1): List<Anime>

    /**
     * Searches for anime by title query.
     *
     * @param query The search query string
     * @param page The page number for pagination (default: 1)
     * @return List of [Anime] objects matching the search query
     */
    suspend fun searchAnime(query: String, page: Int = 1): List<Anime>
}